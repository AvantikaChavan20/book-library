package com.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BookDTO {
  private Long id;

  @NotBlank(message = "title is required")
  @Size(max = 200, message = "title must be at most 200 characters")
  private String title;

  @NotBlank(message = "author is required")
  @Size(max = 200, message = "author must be at most 200 characters")
  private String author;

  @NotBlank(message = "genre is required")
  @Size(max = 50, message = "genre must be at most 50 characters")
  private String genre;

  @NotNull(message = "rating is required")
  @Min(value = 1, message = "rating must be between 1 and 5")
  @Max(value = 5, message = "rating must be between 1 and 5")
  private Integer rating;

  @NotBlank(message = "description is required")
  @Size(max = 2000, message = "description must be at most 2000 characters")
  private String description;

  @NotBlank(message = "coverColor is required")
  @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "coverColor must be a hex color like #7c3aed")
  private String coverColor;

  @NotNull(message = "publishedYear is required")
  @Min(value = 0, message = "publishedYear must be a valid year")
  @Max(value = 2100, message = "publishedYear must be a valid year")
  private Integer publishedYear;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCoverColor() {
    return coverColor;
  }

  public void setCoverColor(String coverColor) {
    this.coverColor = coverColor;
  }

  public Integer getPublishedYear() {
    return publishedYear;
  }

  public void setPublishedYear(Integer publishedYear) {
    this.publishedYear = publishedYear;
  }
}
