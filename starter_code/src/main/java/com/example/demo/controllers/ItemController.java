package com.example.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

import static com.example.demo.security.SecurityConstants.*;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	private Logger logger = LoggerFactory.getLogger(ItemController.class);

	private ItemRepository itemRepository;

	public ItemController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
		return ResponseEntity.ok(itemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		logger.info("Method: {}, Status: {} , ItemById: {}", "getItemById", SUCCESS, id);
		return ResponseEntity.of(itemRepository.findById(id));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		List<Item> items = itemRepository.findByName(name);
		if (items == null || items.isEmpty()) {
			logger.error("Method: {}, Status: {} , ItemsByName: {}", "getItemsByName", FAIL, "not found");
			return ResponseEntity.notFound().build();
		}
		logger.info("Method: {}, Status: {} , ItemsByName: {}", "getItemsByName", SUCCESS, name);
		return ResponseEntity.ok(items);
			
	}
	
}
