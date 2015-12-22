package com.danielbchapman.application.functional;

public interface Function<Arguments, Values>
{
  public Values call(Arguments args);
}
