package com.beyazpoliss.emperor.koth.api;

import org.jetbrains.annotations.NotNull;

/**
 * Enum representing status.
 */
public enum Status {
  NOT_STARTED("Not-Started"),
  WAITING("Waiting"),
  FIGHTING("Fighting"),
  ENDING("Ending");

  @NotNull
  private final String value;

  /**
   * Constructor for the Status enum.
   *
   * @param value the value representing the status
   */
  Status(@NotNull final String value) {
    this.value = value;
  }

  /**
   * Returns the string value of the status.
   *
   * @return the string value of the status
   */
  @NotNull
  public String getValue() {
    return value;
  }

  /**
   * Parses the given string value and returns the corresponding Status enum constant.
   * If no match is found, null is returned.
   *
   * @param value the string value to parse
   * @return the corresponding Status enum constant, or null if no match is found
   */
  public static Status fromValue(String value) {
    for (Status status : Status.values()) {
      if (status.value.equalsIgnoreCase(value)) {
        return status;
      }
    }
    return null;
  }
}