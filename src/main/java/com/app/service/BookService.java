package com.app.service;

import com.app.dto.BookDTO;
import com.app.entity.Book;
import com.app.exception.BookNotFoundException;
import com.app.repository.BookRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Transactional(readOnly = true)
  public List<BookDTO> getAll() {
    return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(BookService::toDto).toList();
  }

  @Transactional(readOnly = true)
  public BookDTO getById(Long id) {
    return toDto(getEntityById(id));
  }

  @Transactional
  public BookDTO create(BookDTO dto) {
    Book book = new Book();
    applyDto(book, dto);
    Book saved = bookRepository.save(book);
    return toDto(saved);
  }

  @Transactional
  public BookDTO update(Long id, BookDTO dto) {
    Book book = getEntityById(id);
    applyDto(book, dto);
    Book saved = bookRepository.save(book);
    return toDto(saved);
  }

  @Transactional
  public void delete(Long id) {
    Book book = getEntityById(id);
    bookRepository.delete(book);
  }

  @Transactional(readOnly = true)
  public List<BookDTO> search(String keyword) {
    if (keyword == null || keyword.trim().isEmpty()) {
      return getAll();
    }
    String k = keyword.trim();
    return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(k, k)
        .stream()
        .map(BookService::toDto)
        .toList();
  }

  private Book getEntityById(Long id) {
    return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
  }

  private static void applyDto(Book book, BookDTO dto) {
    book.setTitle(dto.getTitle());
    book.setAuthor(dto.getAuthor());
    book.setGenre(dto.getGenre());
    book.setRating(dto.getRating());
    book.setDescription(dto.getDescription());
    book.setCoverColor(dto.getCoverColor());
    book.setPublishedYear(dto.getPublishedYear());
  }

  private static BookDTO toDto(Book book) {
    BookDTO dto = new BookDTO();
    dto.setId(book.getId());
    dto.setTitle(book.getTitle());
    dto.setAuthor(book.getAuthor());
    dto.setGenre(book.getGenre());
    dto.setRating(book.getRating());
    dto.setDescription(book.getDescription());
    dto.setCoverColor(book.getCoverColor());
    dto.setPublishedYear(book.getPublishedYear());
    return dto;
  }
}
