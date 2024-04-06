package com.example.docgeneratorspring.controller;

import com.example.docgeneratorspring.entity.Car;
import com.example.docgeneratorspring.entity.Client;
import com.example.docgeneratorspring.repository.CarRepository;
import com.example.docgeneratorspring.repository.ClientRepository;
import com.example.docgeneratorspring.util.WordTextReplacer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class ContentController {
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final WordTextReplacer wordTextReplacer;
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/cars")
    public String carsPage(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "cars";
    }

    @GetMapping("/clients")
    public String clientsPage(Model model) {
        List<Client> clients = clientRepository.findAll();
        clients.sort(new Comparator<Client>() {
            @Override
            public int compare(Client client1, Client client2) {
                return client1.getClSurname().toUpperCase(Locale.forLanguageTag("RU"))
                        .compareTo(client2.getClSurname().toUpperCase(Locale.forLanguageTag("RU")));
            }
        });
        model.addAttribute("clients",clients);
        return "clients";
    }

    @GetMapping("/addCar")
    public String addCarPage() {
        return "addCar";
    }

    @GetMapping("/addClient")
    public String addClientPage() {
        return "addClient";
    }

    @PostMapping("/addClient")
    public String addClient(Client client) {
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @PostMapping("/addCar")
    public String addCar(Car car) {
        carRepository.save(car);
        return "redirect:/cars";
    }

    @PostMapping("/deleteCar/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Long carId) {
        try {
            carRepository.deleteById(carId);
            return ResponseEntity.ok("Car deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete car");
        }
    }

    @PostMapping("/deleteClient/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable Long clientId) {
        try {
            clientRepository.deleteById(clientId);
            return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete client", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/modifyCar/{carId}")
    public String modifyCarPage(@PathVariable Long carId, Model model){
        Optional<Car> carOptional = carRepository.findById(carId);
        carOptional.ifPresent(car -> model.addAttribute("car", car));
        return "modifyCar";
    }

    @GetMapping("/modifyClient/{clientId}")
    public String modifyClientPage(@PathVariable Long clientId, Model model){
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        clientOptional.ifPresent(client -> model.addAttribute("client", client));
        return "modifyClient";
    }
    @PostMapping("/updateCar")
    public String updateCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/cars";
    }
    @PostMapping("/updateClient")
    public String updateClient(@ModelAttribute Client client) {
        clientRepository.save(client);
        return "redirect:/clients";
    }
    @GetMapping("/doc")
    public String docPage(Model model){
        List<Car> cars = carRepository.findAll();
        List<Client> clients = clientRepository.findAll();
        clients.sort(new Comparator<Client>() {
            @Override
            public int compare(Client client1, Client client2) {
                return client1.getClSurname().toUpperCase(Locale.forLanguageTag("RU"))
                        .compareTo(client2.getClSurname().toUpperCase(Locale.forLanguageTag("RU")));
            }
        });
        model.addAttribute("cars",cars);
        model.addAttribute("clients",clients);
        return "doc";
    }
    @GetMapping("/docAct")
    public String docActPage(Model model){
        List<Car> cars = carRepository.findAll();
        List<Client> clients = clientRepository.findAll();
        clients.sort(new Comparator<Client>() {
            @Override
            public int compare(Client client1, Client client2) {
                return client1.getClSurname().toUpperCase(Locale.forLanguageTag("RU"))
                        .compareTo(client2.getClSurname().toUpperCase(Locale.forLanguageTag("RU")));
            }
        });
        model.addAttribute("cars",cars);
        model.addAttribute("clients",clients);
        return "docAct";
    }
    @PostMapping("/docAct")
    @ResponseBody
    public Map<String, Long> docActGen(@RequestBody Map<String, List<Long>> requestBody, RedirectAttributes redirectAttributes) {
        List<Long> selectedCars = requestBody.get("selectedCars");
        List<Long> selectedClients = requestBody.get("selectedClients");
        Map<String, Long> redirectData = new HashMap<>();
        redirectData.put("carId", selectedCars.get(0));
        redirectData.put("clientId", selectedClients.get(0));
        return redirectData;
    }
    @PostMapping("/doc")
    @ResponseBody
    public Map<String, Long> docGen(@RequestBody Map<String, List<Long>> requestBody, RedirectAttributes redirectAttributes) {
        List<Long> selectedCars = requestBody.get("selectedCars");
        List<Long> selectedClients = requestBody.get("selectedClients");
        Map<String, Long> redirectData = new HashMap<>();
        redirectData.put("carId", selectedCars.get(0));
        redirectData.put("clientId", selectedClients.get(0));

        return redirectData;
    }
    @GetMapping("/enddoc")
    public String endDocPage(@RequestParam("carId") Long carId, @RequestParam("clientId") Long clientId, Model model){
        model.addAttribute("carId",carId);
        model.addAttribute("clientId",clientId);

        return "enddoc";
    }
    @GetMapping("/enddocAct")
    public String endDocActPage(@RequestParam("carId") Long carId, @RequestParam("clientId") Long clientId, Model model){
        model.addAttribute("carId",carId);
        model.addAttribute("clientId",clientId);
        return "enddocact";
    }
    @PostMapping("/confirmEnddoc")
    public String confirmEndDoc(@RequestParam("carId") Long carId,
                                @RequestParam("clientId") Long clientId,
                                @RequestParam("docNumber") String docNumber,
                                Model model){
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        wordTextReplacer.generateDoc(clientOptional.get(),docNumber);

        return "redirect:/uploadDoc";
    }
    @PostMapping("/confirmEnddocAct")
    public String confirmEndDocAct(@RequestParam("carId") Long carId,
                                   @RequestParam("clientId") Long clientId,
                                   @RequestParam("docNumber") String docNumber,
                                   @RequestParam("docDate") String docDate,
                                   @RequestParam("priceInDay") String priceInDay,
                                   @RequestParam("finalPrice") String finalPrice,
                                   @RequestParam("allRentDays") String allRentDays,
                                   @RequestParam("dayRentStartTime") String dayRentStartTime,
                                   @RequestParam("dayRentStartDays") String dayRentStartDays,
                                   @RequestParam("dayRentEndTime") String dayRentEndTime,
                                   @RequestParam("dayRentEndDays") String dayRentEndDays,
                                   @RequestParam("address") String address,
                                   @RequestParam("fuel") String fuel,
                                   Model model){
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        Optional<Car> carOptional = carRepository.findById(carId);
        wordTextReplacer.generateDocAct(clientOptional.get(),carOptional.get(),docNumber,docDate,priceInDay,finalPrice,allRentDays,dayRentStartTime,dayRentStartDays,dayRentEndTime,dayRentEndDays,address,fuel);

        return "redirect:/uploadDocAct";
    }
    @GetMapping("/uploadDoc")
    public ResponseEntity<byte[]> downloadDoc() throws IOException {
        String filePath = "src/main/resources/static/modifiedDoc.docx";

        InputStream inputStream = new FileInputStream(filePath);
        byte[] docBytes = inputStream.readAllBytes();

        MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header("Content-Disposition", "attachment; filename=downloadedFile.docx")
                .body(docBytes);
    }

    @GetMapping("/uploadDocAct")
    public ResponseEntity<byte[]> downloadDocAct() throws IOException {
        String filePath = "src/main/resources/static/modifiedDocAct.docx";

        InputStream inputStream = new FileInputStream(filePath);
        byte[] docBytes = inputStream.readAllBytes();

        MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header("Content-Disposition", "attachment; filename=downloadedFile.docx")
                .body(docBytes);
    }
}
