package com.springboot.example.springwebservice.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.example.springwebservice.rest.Isbn13ValidationRequest;
import com.springboot.example.springwebservice.rest.exception.InvalidIsbn13LengthException;
import com.springboot.example.springwebservice.rest.service.Isbn13ValidationService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
@AutoConfigureMockMvc
class Isbn13ValidationControllerTest {
  @MockBean
  private Isbn13ValidationService isbn13ValidationService;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;

  @ParameterizedTest
  @ValueSource(strings = { // examples that do not contain valid isbn13 request format
    "978;0374275631", /* should not have a semicolon */
    "978?0374275631", /* should not have a question mark */
    "978037427,5631", /* should not have a comma */
    "978037.4275631", /* should not have a period */
    "97a-0374275631", /* should not have a lower case alphabet (in this case "a") */
    "978037427563C", /* should not have an upper case alphabet (in this case "C") */
    "", /* should not have empty or blank string */
    "       ",
    "1234 12345 1", /* should not have fewer than 13 characters */
  })
  public void givenInvalidRequestWithInvalidIsbn13Format_whenIsbn13Validation_thenShouldReturnBadRequestStatusAndNotCallValidationService(
    String isbn13WithInvalidFormat
  ) throws Exception {
    // given
    Isbn13ValidationRequest request = new Isbn13ValidationRequest();
    request.setIsbn13Value(isbn13WithInvalidFormat);

    // when
    ResultActions resultActions = mockMvc.perform(
      MockMvcRequestBuilders
        .post("/api/v1/isbn13-validation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    );

    // then
    resultActions
      .andExpect(MockMvcResultMatchers.status().isBadRequest());

    BDDMockito
      .then(isbn13ValidationService)
      .shouldHaveNoInteractions();
  }

  @Test
  public void givenNullIsbn13Value_whenIsbn13Validation_thenShouldReturnBadRequestStatusAndNotCallValidationService() throws Exception {
    // given
    Isbn13ValidationRequest request = new Isbn13ValidationRequest();
    request.setIsbn13Value(null);

    // when
    ResultActions resultActions = mockMvc.perform(
      MockMvcRequestBuilders
        .post("/api/v1/isbn13-validation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    );

    // then
    resultActions
      .andExpect(MockMvcResultMatchers.status().isBadRequest());

    BDDMockito
      .then(isbn13ValidationService)
      .shouldHaveNoInteractions();
  }

  @Test
  public void givenValidRequestAndIsInvalidIsbn13LengthExceptionThrown_whenIsbn13Validation_thenShouldReturnBadRequestStatusAndCallValidationServiceOnce() throws Exception {
    // given
    Isbn13ValidationRequest request = new Isbn13ValidationRequest();
    request.setIsbn13Value("978-0374275631");

    BDDMockito
      .given(isbn13ValidationService.test(BDDMockito.any()))
      .willThrow(InvalidIsbn13LengthException.class);

    // when
    ResultActions resultActions = mockMvc.perform(
      MockMvcRequestBuilders
        .post("/api/v1/isbn13-validation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    );

    // then
    resultActions
      .andExpect(MockMvcResultMatchers.status().isBadRequest());

    BDDMockito
      .then(isbn13ValidationService)
      .should(BDDMockito.times(1))
      .test(BDDMockito.any());
  }

  @ParameterizedTest
  @CsvSource({
    "978-0374275631,  true",
    "978-0374275631,  false"
  })
  public void givenValidRequestWithValidIsbn13Format_whenIsbn13Validation_thenShouldHaveOkStatusAndReturnValidationResponseAndCallValidationServiceOnce(
    String isbn13WithValidFormat, boolean booleanResult
  ) throws Exception {
    // given
    Isbn13ValidationRequest request = new Isbn13ValidationRequest();
    request.setIsbn13Value(isbn13WithValidFormat);
    ArgumentCaptor<Isbn13ValidationRequest> requestArgumentCaptor = ArgumentCaptor.forClass(Isbn13ValidationRequest.class);

    BDDMockito
      .given(isbn13ValidationService.test(requestArgumentCaptor.capture()))
      .willReturn(booleanResult);


    // when
    ResultActions resultActions = mockMvc.perform(
      MockMvcRequestBuilders
        .post("/api/v1/isbn13-validation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    );

    // then
    resultActions
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$.isValid", CoreMatchers.is(booleanResult)));

    BDDMockito
      .then(isbn13ValidationService)
      .should(BDDMockito.times(1))
      .test(BDDMockito.any());

    assertThat(requestArgumentCaptor.getValue()).isNotNull();
    assertThat(requestArgumentCaptor.getValue().getIsbn13Value()).isEqualTo(isbn13WithValidFormat);
  }
}