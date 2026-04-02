package com.app.controller;

import com.app.dto.BookDTO;
import com.app.service.BookService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/books")
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public List<BookDTO> getAll() {
    return bookService.getAll();
  }

  @GetMapping("/{id}")
  public BookDTO getById(@PathVariable Long id) {
    return bookService.getById(id);
  }

  @PostMapping
  public ResponseEntity<BookDTO> create(@Valid @RequestBody BookDTO dto) {
    BookDTO created = bookService.create(dto);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(created.getId())
        .toUri();
    return ResponseEntity.created(location).body(created);
  }

  @PutMapping("/{id}")
  public BookDTO update(@PathVariable Long id, @Valid @RequestBody BookDTO dto) {
    return bookService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    bookService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public List<BookDTO> search(@RequestParam(required = false) String keyword) {
    return bookService.search(keyword);
  }
}
