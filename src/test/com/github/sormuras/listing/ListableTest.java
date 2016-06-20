package com.github.sormuras.listing;

import static com.github.sormuras.listing.Listable.IDENTITY;
import static com.github.sormuras.listing.Listable.NEWLINE;
import static com.github.sormuras.listing.Listable.SPACE;
import static org.junit.gen5.api.Assertions.assertArrayEquals;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.gen5.api.Test;

class ListableTest {

  class A implements Listable {

    @Override
    public Listing apply(Listing t) {
      return t;
    }

    @Override
    public String toString() {
      return "a";
    }
  }

  class Z implements Listable {

    @Override
    public Listing apply(Listing t) {
      return t;
    }

    @Override
    public String toString() {
      return "z";
    }
  }

  @Test
  void compare() {
    Listable[] expecteds = {new A(), l -> l, new Z()};
    Listable[] actuals = {expecteds[2], expecteds[0], expecteds[1]};
    Arrays.sort(actuals);
    assertArrayEquals(expecteds, actuals);
  }

  @Test
  void comparisonKey() {
    assertEquals("a#a", new A().comparisonKey());
    assertEquals("z#z", new Z().comparisonKey());
    assertTrue(IDENTITY.comparisonKey().startsWith("listable$$lambda"));
  }

  @Test
  void empty() {
    assertTrue(new A().isEmpty());
    assertTrue(IDENTITY.isEmpty());
    assertTrue(NEWLINE.isEmpty());
    assertFalse(SPACE.isEmpty());
  }

  @Test
  void of() {
    assertEquals("int.class", Listable.of(int.class).list());
    assertEquals("java.lang.Thread.State.class", Listable.of(Thread.State.class).list());
    assertEquals("java.lang.Thread.State.NEW", Listable.of(Thread.State.NEW).list());
    assertEquals("\"a\"", Listable.of("a").list());
    assertEquals("2.718282F", Listable.of((float) Math.E).list());
    assertEquals("9223372036854775807L", Listable.of(Long.MAX_VALUE).list());
    assertEquals("'!'", Listable.of('!').list());
    assertEquals("null", Listable.of(null).list());
    assertEquals("0", Listable.of(BigInteger.ZERO).list());
  }
}
