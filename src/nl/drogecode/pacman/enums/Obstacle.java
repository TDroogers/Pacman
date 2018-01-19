/**
 * 
 * The different typ's that can influence a moving object.
 * 
 * Border and Wall will alway's stop an man, an Intersection only give's the ghost's the chance to recalculate it's direction.
 * 
 */
package nl.drogecode.pacman.enums;

public enum Obstacle
{
  BORDER, WALL, INTERSECTION;
}
