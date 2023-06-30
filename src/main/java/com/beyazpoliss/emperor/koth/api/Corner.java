package com.beyazpoliss.emperor.koth.api;

import com.beyazpoliss.emperor.koth.game.ZoneCorner;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * The Corner interface represents a corner point in a three-dimensional space.
 * It provides methods to retrieve and set the coordinates of the corner.
 */
public interface Corner {

  /**
   * Returns the x-coordinate of the corner.
   *
   * @return the x-coordinate of the corner
   */
  double x();

  /**
   * Returns the y-coordinate of the corner.
   *
   * @return the y-coordinate of the corner
   */
  double y();

  /**
   * Returns the z-coordinate of the corner.
   *
   * @return the z-coordinate of the corner
   */
  double z();

  /**
   * Sets the x-coordinate of the corner.
   *
   * @param x the new x-coordinate of the corner
   */
  void x(double x);

  /**
   * Sets the y-coordinate of the corner.
   *
   * @param y the new y-coordinate of the corner
   */
  void y(double y);

  /**
   * Sets the z-coordinate of the corner.
   *
   * @param z the new z-coordinate of the corner
   */
  void z(double z);

  static Corner of(@NotNull final Location location){
    return new ZoneCorner(location.getBlockX(), location.getBlockY(), location.getBlockZ());
  }
}
