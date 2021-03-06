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

package com.github.sormuras.listing.unit;

import com.github.sormuras.listing.Listable;
import com.github.sormuras.listing.Listing;
import java.util.ArrayList;
import java.util.List;

/**
 * An enum declaration specifies a new enum type, a special kind of class type.
 *
 * @see https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.9
 */
public class EnumDeclaration extends ClassDeclaration {

  private final List<EnumConstant> constants = new ArrayList<>();

  @Override
  public Listing apply(Listing listing) {
    if (!isLocal()) {
      listing.newline();
    }
    // {ClassModifier}
    listing.add(toAnnotationsListable());
    listing.add(toModifiersListable());
    // enum Identifier
    listing.add("enum").add(' ').add(getName());
    // [Superinterfaces]
    if (!isInterfacesEmpty()) {
      listing.add(" implements ").add(getInterfaces(), ", ");
    }
    listing.add(' ').add('{').newline();
    listing.indent(1);
    listing.push(getName());
    // [EnumConstantList]
    listing.add(getConstants(), l -> l.add(",").newline());
    if (!isEmpty()) {
      listing.add(';');
      listing.newline();
      applyClassBodyElements(listing);
    } else if (!getConstants().isEmpty()) {
      listing.newline();
    }
    listing.pop();
    listing.indent(-1).add('}').newline();
    return listing;
  }

  /** Add new enum constant. */
  public EnumConstant declareConstant(String name) {
    return declareConstant(name, null);
  }

  /** Add new enum constant. */
  public EnumConstant declareConstant(String name, Listable arguments) {
    return declareConstant(name, arguments, null);
  }

  /** Add new enum constant. */
  public EnumConstant declareConstant(String name, Listable arguments, ClassDeclaration body) {
    EnumConstant constant = new EnumConstant();
    constant.setEnclosingDeclaration(this);
    constant.setCompilationUnit(getCompilationUnit());
    constant.setName(name);
    constant.setArguments(arguments);
    constant.setBody(body);
    getConstants().add(constant);
    return constant;
  }

  public List<EnumConstant> getConstants() {
    return constants;
  }
}
