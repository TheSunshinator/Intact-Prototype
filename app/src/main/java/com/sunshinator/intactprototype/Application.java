package com.sunshinator.intactprototype;

public class Application extends android.app.Application {

  @Override
  public void onCreate() {

    super.onCreate();

    DatabaseSimulator.init( this );
  }
}
