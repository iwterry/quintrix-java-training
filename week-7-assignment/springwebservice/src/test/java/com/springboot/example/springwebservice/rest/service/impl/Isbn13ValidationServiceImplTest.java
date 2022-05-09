package com.springboot.example.springwebservice.rest.service.impl;

import com.springboot.example.springwebservice.consumingwebservice.Isbn13ValidatorClient;
import com.springboot.example.springwebservice.consumingwebservice.wsdl.IsValidISBN13Response;
import com.springboot.example.springwebservice.rest.Isbn13ValidationRequest;
import com.springboot.example.springwebservice.rest.Isbn13ValidationResponse;
import com.springboot.example.springwebservice.rest.exception.InvalidIsbn13LengthException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class Isbn13ValidationServiceImplTest {
  @Mock
  private Isbn13ValidatorClient client;
  @Captor
  ArgumentCaptor<String> stringArgumentCaptor;
  private Isbn13ValidationServiceImpl underTest;
  private IsValidISBN13Response isValidISBN13Response;


  @BeforeEach
  void setUp() {
    underTest = new Isbn13ValidationServiceImpl(client);
    isValidISBN13Response = new IsValidISBN13Response();
  }

  /*
    NOTE: a valid isbn 13 format is assumed to consist of only 13 digits and
    can have any number of spaces and hyphens.
    A valid isbn 13 value is on that has both valid format and passes the client's validation.
   */

  @ParameterizedTest
  @ValueSource(strings = {
    "978-037427563 1",
    "978 037427563-1",
    "978-037   4275- 631",
    "978-037 4 2-7-5 6 -3 1",
    "-978-0374275631 ",
    " 978-0374275631-"
  })
  void givenRequestWithValidIsbn13Value_whenTest_thenShouldReturnTrueAndCallValidatorClientOnceWithAppropriateArgument(String validIsbn13Value) {
    // given
    isValidISBN13Response.setIsValidISBN13Result(true);
    BDDMockito
      .given(client.getIsIsbn13Valid(stringArgumentCaptor.capture()))
      .willReturn(isValidISBN13Response);

    Isbn13ValidationRequest request = new Isbn13ValidationRequest();
    request.setIsbn13Value(validIsbn13Value);

    // when
    boolean isIsbn13ValueValid = underTest.test(request);

    // then
    assertThat(isIsbn13ValueValid).isTrue();
    BDDMockito
      .then(client)
      .should(BDDMockito.times(1))
      .getIsIsbn13Valid(BDDMockito.any());

    assertThat(stringArgumentCaptor.getValue()).isEqualTo("9780374275631");
  }

  @ParameterizedTest
  @CsvSource({
    "978-0374275631, true",
    "978-0374275631, false"
  })
  public void givenRequestWithValidIsbn13Format_whenTest_thenShouldReturnBooleanResultFromValidatorClientAfterCallingClientOnlyOnce(String isbn13Value, boolean booleanFromValidatorClient) {
    // given
    isValidISBN13Response.setIsValidISBN13Result(booleanFromValidatorClient);
    BDDMockito
      .given(client.getIsIsbn13Valid(stringArgumentCaptor.capture()))
      .willReturn(isValidISBN13Response);

    Isbn13ValidationRequest request = new Isbn13ValidationRequest();
    request.setIsbn13Value(isbn13Value);

    // when
    boolean isIsbn13ValueValid = underTest.test(request);

    // then
    assertThat(isIsbn13ValueValid).isEqualTo(booleanFromValidatorClient);
    BDDMockito
      .then(client)
      .should(BDDMockito.times(1))
      .getIsIsbn13Valid(BDDMockito.any());

    assertThat(stringArgumentCaptor.getValue()).isEqualTo("9780374275631");
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "978-037427563-12", /* more than 13 digits */
    "978-037427563-" /* less than 13 digits */
  })
  void givenRequestWithIsbn13ValueWithInvalidNumOfDigits_whenTest_thenShouldThrowAnExceptionAndNoCallToValidatorClientShouldOccur(String invalidIsbn13Value) {
    // given
    Isbn13ValidationRequest request = new Isbn13ValidationRequest();
    request.setIsbn13Value(invalidIsbn13Value);

    // when
    assertThatThrownBy(
      () -> underTest.test(request)
    ).isInstanceOf(InvalidIsbn13LengthException.class);

    // then
    BDDMockito
      .then(client)
      .shouldHaveNoInteractions();
  }
}