/*
 * Copyright (C) 2016 Christian Stein
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.codeturm.listing;

import java.util.EnumSet;
import java.util.Set;
import java.util.TreeSet;

import javax.lang.model.element.Modifier;

/**
 * An <b>instance</b> initializer declared in a class is executed when an instance of the class is
 * created and a <b>static</b> initializer declared in a class is executed when the class is
 * initialized.
 *
 * @author Christian Stein
 * @see https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.6
 * @see https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.7
 */
public class Initializer extends Block implements Modified {

  private ClassDeclaration enclosing;
  private Set<Modifier> modifiers;

  @Override
  public Lines apply(Lines lines) {
    if (isStatic()) {
      lines.add("static ");
    }
    return super.apply(lines);
  }

  public ClassDeclaration getEnclosing() {
    return enclosing;
  }

  @Override
  public Set<Modifier> getModifiers() {
    if (modifiers == null) {
      modifiers = new TreeSet<>();
    }
    return modifiers;
  }

  @Override
  public Set<Modifier> getModifierValidationSet() {
    return EnumSet.of(Modifier.STATIC);
  }

  public void setEnclosing(ClassDeclaration enclosing) {
    this.enclosing = enclosing;
  }
}