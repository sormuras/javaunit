package com.github.sormuras.listing.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.sormuras.listing.Name;
import com.github.sormuras.listing.Tests;
import com.github.sormuras.listing.type.ClassType;
import com.github.sormuras.listing.type.JavaType;
import com.github.sormuras.listing.type.TypeArgument;
import com.github.sormuras.listing.type.WildcardType;
import java.lang.annotation.ElementType;
import java.util.Formatter;
import org.junit.jupiter.api.Test;

class AnnotationDeclarationTest {

  @Test
  void empty() {
    TypeDeclaration declaration = new AnnotationDeclaration();
    declaration.setName("Empty");
    assertEquals("@interface Empty {\n}\n", declaration.list());
    assertTrue(declaration.isEmpty());
    assertFalse(
        new AnnotationDeclaration()
            .declareConstant(JavaType.of(int.class), "constant", l -> l)
            .getEnclosingDeclaration()
            .isEmpty());
    assertFalse(
        new AnnotationDeclaration()
            .declareElement(JavaType.of(int.class), "element")
            .getEnclosingDeclaration()
            .isEmpty());
  }

  @Test
  void everything() {
    WildcardType extendsFormatter = new WildcardType();
    extendsFormatter.setBoundExtends(ClassType.of(Formatter.class));
    AnnotationDeclaration declaration = new AnnotationDeclaration();
    declaration.setName("Everything");
    declaration.declareConstant(JavaType.of(String.class), "EMPTY_TEXT", "");
    declaration
        .declareConstant(JavaType.of(float.class), "PI", l -> l.add("3.141F"))
        .addAnnotation(Deprecated.class);
    declaration.declareConstant(JavaType.of(double.class), "E", Name.of(Math.class, "E"));
    declaration.declareElement(JavaType.of(int.class), "id");
    declaration
        .declareElement(JavaType.of(String.class), "date", "201608032129")
        .addAnnotation(Deprecated.class);
    declaration.declareElement(
        ClassType.of(Name.of(Class.class), TypeArgument.of(extendsFormatter)), "formatterClass");
    Tests.assertEquals(getClass(), "everything", declaration);
    assertFalse(declaration.isEmpty());
  }

  @Test
  void target() {
    assertEquals(ElementType.TYPE, new AnnotationDeclaration().getAnnotationTarget());
  }
}
