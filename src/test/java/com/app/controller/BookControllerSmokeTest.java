package com.app.controller;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.dto.BookDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerSmokeTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void getAll_returnsSeededBooks() throws Exception {
    mockMvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(8)));
  }

  @Test
  void getById_returnsSingleBook() throws Exception {
    mockMvc.perform(get("/api/books/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.title", is("To Kill a Mockingbird")));
  }

  @Test
  void search_findsByTitleOrAuthor() throws Exception {
    mockMvc.perform(get("/api/books/search").param("keyword", "orwell"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].title", is("1984")));
  }

  @Test
  void createUpdateDelete_roundTrip() throws Exception {
    BookDTO dto = new BookDTO();
    dto.setTitle("Test Book");
    dto.setAuthor("Someone");
    dto.setGenre("Tech");
    dto.setRating(4);
    dto.setDescription("Just a test.");
    dto.setCoverColor("#7c3aed");
    dto.setPublishedYear(2024);

    String createdJson = mockMvc
        .perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/books/")))
        .andExpect(jsonPath("$.id", greaterThanOrEqualTo(9)))
        .andExpect(jsonPath("$.title", is("Test Book")))
        .andReturn()
        .getResponse()
        .getContentAsString();

    BookDTO created = objectMapper.readValue(createdJson, BookDTO.class);

    dto.setTitle("Test Book Updated");
    dto.setRating(5);

    mockMvc.perform(put("/api/books/{id}", created.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(created.getId().intValue())))
        .andExpect(jsonPath("$.title", is("Test Book Updated")))
        .andExpect(jsonPath("$.rating", is(5)));

    mockMvc.perform(delete("/api/books/{id}", created.getId()))
        .andExpect(status().isNoContent());

    mockMvc.perform(get("/api/books/{id}", created.getId()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status", is(404)))
        .andExpect(jsonPath("$.path", is("/api/books/" + created.getId())));
  }
}
