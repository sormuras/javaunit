package com.github.sormuras.listing;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;

import org.junit.Assert;
import org.junit.Test;

public class JavaMirrorsTest {

  @Test
  public void primitives() throws Exception {
    JavaUnit unit = new JavaUnit("test");
    ClassDeclaration type = unit.declareClass("PrimitiveFields").addModifier(Modifier.PUBLIC);
    type.declareField(boolean.class, "field1").addAnnotation(Counter.Mark.class);
    type.declareField(byte.class, "field2").addAnnotation(Counter.Mark.class);
    type.declareField(char.class, "field3").addAnnotation(Counter.Mark.class);
    type.declareField(double.class, "field4").addAnnotation(Counter.Mark.class);
    type.declareField(float.class, "field5").addAnnotation(Counter.Mark.class);
    type.declareField(int.class, "field6").addAnnotation(Counter.Mark.class);
    type.declareField(long.class, "field7").addAnnotation(Counter.Mark.class);
    type.declareField(short.class, "field8").addAnnotation(Counter.Mark.class);
    type.declareMethod(void.class, "noop")
        .addAnnotation(Counter.Mark.class)
        .setBody(l -> l.add("{}"));

    Counter counter = new Counter();
    Compilation.compile(getClass().getClassLoader(), emptyList(), asList(counter), unit);

    assertEquals(9, counter.map.size());
    assertEquals(JavaType.of(boolean.class), counter.map.get("field1"));
    assertEquals(JavaType.of(byte.class), counter.map.get("field2"));
    assertEquals(JavaType.of(char.class), counter.map.get("field3"));
    assertEquals(JavaType.of(double.class), counter.map.get("field4"));
    assertEquals(JavaType.of(float.class), counter.map.get("field5"));
    assertEquals(JavaType.of(int.class), counter.map.get("field6"));
    assertEquals(JavaType.of(long.class), counter.map.get("field7"));
    assertEquals(JavaType.of(short.class), counter.map.get("field8"));
    assertEquals(JavaType.of(void.class), counter.map.get("noop"));
  }

  @Test
  public void unknownTypeFails() {
    try {
      JavaMirrors.of(Text.proxy(PrimitiveType.class, (p, m, a) -> TypeKind.ERROR));
      Assert.fail();
    } catch (AssertionError e) {
      Assert.assertTrue(e.toString().contains("Unsupported primitive type"));
    }
    try {
      JavaMirrors.of(Text.proxy(NoType.class, (p, m, a) -> TypeKind.ERROR));
      Assert.fail();
    } catch (AssertionError e) {
      Assert.assertTrue(e.toString().contains("Unsupported no type"));
    }
  }
}