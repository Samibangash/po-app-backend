package com.poapp.controller;

import com.itextpdf.text.DocumentException;
import com.poapp.common.ApiResponse;
import com.poapp.dto.PurchaseOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.poapp.mapper.DTOMapper;
import com.poapp.model.Item;
import com.poapp.model.PurchaseOrder;
import com.poapp.model.User;
import com.poapp.service.PdfService;
import com.poapp.service.PurchaseOrderService;
import com.poapp.service.UserService;
import com.poapp.util.JwtUtil;

import java.io.ByteArrayOutputStream;
import org.springframework.http.MediaType;



import io.jsonwebtoken.io.IOException;

import java.util.List;  // Correct import for List
import java.util.stream.Collectors;  // For stream operations


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

// import com.poapp.service.UserService;

import java.util.Optional;


@RestController
@RequestMapping("/api/po")
public class PurchaseOrderController {

    @Autowired
    private DTOMapper dtoMapper; 

    @Autowired
    private PurchaseOrderService poService;
    
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    


    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> createPurchaseOrder(@RequestBody PurchaseOrderDTO poDTO) {
        // Get the current authenticated user's username or ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming username is the principal

        // Convert DTO to Entity
        PurchaseOrder po = dtoMapper.toPurchaseOrderEntity(poDTO);

        User currentUser = userService.findByUsername(username);
        po.setUser(currentUser);
        List<Item> items = poDTO.getItems().stream()
                                .map(dtoMapper::toItemEntity)
                                .collect(Collectors.toList());

        // Create Purchase Order
        PurchaseOrder createdPO = poService.createPurchaseOrder(po, items);

        // Convert Entity to DTO
        PurchaseOrderDTO createdPODTO = dtoMapper.toPurchaseOrderDTO(createdPO);

        // Create API Response
        ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
                "Success",
                HttpStatus.OK.value(),
                createdPODTO,
                true
        );

        return ResponseEntity.ok(response);
    }

    

    @PutMapping(value = "/{id}/status", consumes = "application/json")
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> updatePOStatus(@PathVariable Long id, @RequestParam String status) {
        PurchaseOrder updatedPO = poService.updatePOStatus(id, status);
        PurchaseOrderDTO updatedPODTO = dtoMapper.toPurchaseOrderDTO(updatedPO);
        ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
            "Success", 
            HttpStatus.OK.value(), 
            updatedPODTO, 
            true
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> getPurchaseOrderById(@PathVariable Long id) {
        Optional<PurchaseOrder> poOptional = Optional.ofNullable(poService.getPurchaseOrderById(id));
        
        if (!poOptional.isPresent()) {
            ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
                "Purchase Order not found", 
                HttpStatus.NOT_FOUND.value(), 
                null, 
                false
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        PurchaseOrderDTO poDTO = dtoMapper.toPurchaseOrderDTO(poOptional.get());
        ApiResponse<PurchaseOrderDTO> response = new ApiResponse<>(
            "Success", 
            HttpStatus.OK.value(), 
            poDTO, 
            true
        );

        return ResponseEntity.ok(response);
    }



    // @GetMapping(produces = "application/json")
    // public ResponseEntity<ApiResponse<List<PurchaseOrderDTO>>> getAllPurchaseOrders() {
    //     List<PurchaseOrder> poList = poService.getAllPurchaseOrders();
        
    //     if (poList.isEmpty()) {
    //         ApiResponse<List<PurchaseOrderDTO>> response = new ApiResponse<>(
    //             "No Purchase Orders found", 
    //             HttpStatus.NOT_FOUND.value(), 
    //             null, 
    //             false
    //         );
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    //     }
        
    //     List<PurchaseOrderDTO> poDTOList = poList.stream()
    //                                             .map(dtoMapper::toPurchaseOrderDTO)
    //                                             .collect(Collectors.toList());

    //     ApiResponse<List<PurchaseOrderDTO>> response = new ApiResponse<>(
    //         "Success", 
    //         HttpStatus.OK.value(), 
    //         poDTOList, 
    //         true
    //     );

    //     return ResponseEntity.ok(response);
    // }


    @GetMapping(produces = "application/json")
    public ResponseEntity<ApiResponse<List<PurchaseOrderDTO>>> getPurchaseOrdersByStatus(
            @RequestParam(value = "status", required = false) String status,
            @RequestHeader("Authorization") String authorizationHeader) {
    
        // Extract JWT token from Authorization header
        String token = authorizationHeader.replace("Bearer ", "");
    
        // Extract username from the token
        String username = jwtUtil.extractUsername(token);
    
        User currentUser = userService.findByUsername(username);
    
        List<PurchaseOrder> poList;
    
        if (status != null && !status.isEmpty()) {
            // Fetch purchase orders by status if provided
            poList = poService.getPurchaseOrdersByStatus(status);
        } else if ("5".equals(currentUser.getRole())) {
            Long newId = Long.valueOf(currentUser.getId());
            poList = poService.getPurchaseOrderByIdAndUserId(newId);  // Now this works as it expects a List
        } else {
            // Fetch all purchase orders if no status is provided
            poList = poService.getAllPurchaseOrders();
        }
    
        if (poList.isEmpty()) {
            ApiResponse<List<PurchaseOrderDTO>> response = new ApiResponse<>(
                    "No Purchase Orders found",
                    HttpStatus.NOT_FOUND.value(),
                    null,
                    false
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    
        List<PurchaseOrderDTO> poDTOList = poList.stream()
                .map(dtoMapper::toPurchaseOrderDTO)
                .collect(Collectors.toList());
    
        ApiResponse<List<PurchaseOrderDTO>> response = new ApiResponse<>(
                "Success",
                HttpStatus.OK.value(),
                poDTOList,
                true
        );
    
        return ResponseEntity.ok(response);
    }
    

    @Autowired
    private PdfService pdfService;

    @GetMapping("/generate-pdf/{id}")
public ResponseEntity<ByteArrayResource> generatePdf(@PathVariable Long id) {
    try {
        PurchaseOrder po = poService.getPurchaseOrderById(id); // Fetch PO by ID
        if (po == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null); // Return 404 if PO not found
        }

        // Generate PDF as ByteArrayOutputStream
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        pdfService.generatePdf(pdfStream, po);  // Use the stream for PDF generation

        // Convert the PDF to ByteArrayResource
        ByteArrayResource resource = new ByteArrayResource(pdfStream.toByteArray());

        // Set appropriate headers for PDF download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "PO-" + po.getPoNumber() + ".pdf");

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(resource.contentLength())
            .body(resource);  // Return the PDF as ByteArrayResource
    } catch (DocumentException | IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


    


}
