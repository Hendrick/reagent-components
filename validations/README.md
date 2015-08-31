# Client-Side Form Validations

This module provides a wrapped form field that aligns with the Hendrick v2 Style Guide CSS and allows for simplified client-side validation of ClojureScript forms built with Reagent.

## Dependencies

The validation code relies on [Bouncer][1] to validate data. Make sure that you have added Bouncer to your `project.clj`:

[![Clojars Project](http://clojars.org/bouncer/latest-version.svg)](http://clojars.org/bouncer)

## Configuration

Create an atom to store the validation error state:

```
(def object-errors (atom {}))
```

Configure a form's validations using the map format specified in the [Bouncer documentation][1]:

```
(ns example.form
  (:require [bouncer.validators :as v]))

(def object-validator
  {:name   v/required
   :email [v/required v/email]})
```

## Usage

### validated-field

`validated-field` is a Reagent component for use in web forms. It defines a `div class="field"` to contain a form input in line with Hendrick Style, and it will display an error message below the input when one is defined for the specified key in the error atom.

It takes 4 parameters:

1. `key` is the data key for the form input
1. `error-atom` is the atom set up in configuration to hold validation error state
1. `label` is the user-visible label for the form input
1. `body` is the input component itself (text field, select, textarea, etc.)

### validate-key

`validate-key` is a function to call each time you want to validate a particular form input. Most often, this means binding a call to `validate-key` to the `:on-blur` event of your form inputs.

It takes 4 parameters:

1. `key` is the data key that should be validated (usually matches the form input)
1. `object-atom` is the atom that contains the current state of the form input
1. `error-atom` is the atom set up in configuration to hold validation error state
1. `validator` is the Bouncer validation map set up in configuration

`validate-key` extracts the rules for the specified `key` from `validator`, runs the validations for that key against the key's value in `object-atom`, and updates that key's error message in `error-atom`

### validate

`validate` is a function to call when the form is submitted. This usually means binding the function to the `:on-submit` event of the form.

It takes 4 parameters:

1. `e` is a reference to the submit event
1. `object-atom` is the atom that contains the current state of the form's inputs
1. `error-atom` is the atom set up in configuration to hold validation error state
1. `validator` is the Bouncer validation map set up in configuration

`validate` runs all of the validations against `object-atom`, updates the validation errors in `error-atom`, and prevents the form from submitting if any validation errors are found.

## General Tips

It is strongly recommended that rather than calling these functions directly, you wrap them in functions local to your project and/or form to simplify the process of sending similar parameters for similar uses.

[1]: https://github.com/leonardoborges/bouncer
