package com.beyazpoliss.emperor.koth.game;


import com.avaje.ebean.validation.NotNull;
import com.beyazpoliss.emperor.koth.api.Corner;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class ZoneCorner implements Corner {

  @NotNull
  @Setter
  private double x,y,z;

  @Override
  public double x() {
    return x;
  }

  @Override
  public double y() {
    return y;
  }

  @Override
  public double z() {
    return z;
  }

  @Override
  public void x(double x) {
    this.x = x;
  }

  @Override
  public void y(double y) {
    this.y = y;
  }

  @Override
  public void z(double z) {
    this.z = z;
  }
}
