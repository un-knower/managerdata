package com.ufc.user.utils;

import java.util.ArrayList;

public class Rule
{
  private ArrayList<String> must_list = null;
  private ArrayList<String> maybe_list = null;
  private ArrayList<String> stop_list = null;

  public ArrayList<String> getMust_list()
  {
    return this.must_list;
  }

  public void setMust_list(ArrayList<String> paramArrayList)
  {
    this.must_list = paramArrayList;
  }

  public ArrayList<String> getMaybe_list()
  {
    return this.maybe_list;
  }

  public void setMaybe_list(ArrayList<String> paramArrayList)
  {
    this.maybe_list = paramArrayList;
  }

  public ArrayList<String> getStop_list()
  {
    return this.stop_list;
  }

  public void setStop_list(ArrayList<String> paramArrayList)
  {
    this.stop_list = paramArrayList;
  }
}