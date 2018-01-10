<!DOCTYPE html>
<html>
<#include "head.ftl">
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <#include "header.ftl">
  <#include "leftSidebar.ftl">

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h4>
        <a href="/home"><span class="glyphicon glyphicon-home"></span> Home</a> > Event Manager
      </h4>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-xs-12 padding-0">
            <div class="panel panel-info" style="margin: 1em;">
              <div class="panel-heading">
                <h3 class="panel-title"><span class="fa fa-cogs"></span> Event Detail</h3>
              </div>
              <div class="panel-body">
                <!-- left content -->
                <div class="col-xs-6">
                  <div class="col-xs-12 padding-0">
                    <div class="col-xs-2">
                      <label for="name">Name:</label>
                    </div>
                    <div class="col-xs-10">
                      <input type="text" class="form-control" id="name">
                    </div>
                  </div>
                  <div class="col-xs-12 padding-0 margin-top-20">
                    <div class="col-xs-2">
                      <label for="time">Time:</label>
                    </div>
                    <div class="col-xs-10">
                      <div class="form-group">
                          <div class='input-group'>
                              <input id="event_time" type="text" class="form-control" name="daterange" value="" />
                              <span class="input-group-addon">
                                  <span class="glyphicon glyphicon-calendar"></span>
                              </span>
                          </div>
                      </div>
                      <input id="start_time" type="hidden" value="" />
                      <input id="end_time" type="hidden" value="" />
                    </div>
                  </div>
                  <div class="col-xs-12 padding-0 margin-top-20">
                    <div class="col-xs-4">
                      <label for="status">Select status:</label>
                    </div>
                    <div class="col-xs-8">
                      <select class="form-control" id="status">
                        <option value="PENDING">PENDING</option>
                        <option value="ACTIVE">ACTIVE</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-xs-12 margin-top-20">
                    <label for="content">Content:</label>
                  </div>
                  <div class="col-xs-12 padding-0 cover-box">
                    <div class="col-xs-12 padding-0 margin-top-20">
                      <div class="col-xs-5">
                        <div class="col-xs-8 padding-0">
                          <input type="number" min="0" class="form-control" id="money">
                        </div>
                        <div class="col-xs-4">
                          <p>VND</p>
                        </div>
                      </div>
                      <div class="col-xs-2">
                        <p>=</p>
                      </div>
                      <div class="col-xs-5">
                        <div class="col-xs-8 padding-0">
                          <input type="number" min="0" class="form-control" id="point_money">
                        </div>
                        <div class="col-xs-4">
                          <p>Point</p>
                        </div>
                      </div>
                    </div>

                    <div class="col-xs-12 padding-0 margin-top-20">
                      <div class="col-xs-5">
                        <div class="col-xs-8 padding-0">
                          <input type="number" min="0" class="form-control" id="point_giftbox">
                        </div>
                        <div class="col-xs-4">
                          <p>Point</p>
                        </div>
                      </div>
                      <div class="col-xs-2">
                        <p>=</p>
                      </div>
                      <div class="col-xs-5">
                        <p>1 Gift Box</p>
                      </div>
                    </div>

                    <div class="col-xs-12 padding-0 margin-top-20">
                      <div class="col-xs-6">
                        <label class="radio-inline"><input type="radio" name="optradio" value="limit">Limit</label>
                        <label class="radio-inline"><input type="radio" name="optradio" value="unlimit" checked>Unlimit</label>
                      </div>
                      <div class="col-xs-6" style="margin-bottom: 10px;">
                        <div class="col-xs-6 padding-0">
                          <input type="number" min="0" class="form-control" id="max_giftbox" disabled>
                        </div>
                        <div class="col-xs-6">
                          <p>Max Gift Box</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- end left content -->
                <!-- right content -->
                <div class="col-xs-6">
                  <div class="col-xs-12">
                    <label for="time">Gift:</label>
                  </div>
                  <div class="col-xs-12 cover-box">
                    <!-- Card -->
                    <div class="col-xs-12 padding-0 margin-top-20">
                      <label class="checkbox-inline"><input type="checkbox" value="" id="check_card">Card</label>
                      <div class="form-group">
                        <div class="form-inline">
                          <label for="total_percentage_card">Total Percentage of Card:</label>
                          <div class='input-group'>
                              <input type="number" class="form-control input-total-percent-card validate-input-int" value="" disabled/>
                              <span class="input-group-addon">
                                  <span class="fa fa-percent"></span>
                              </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-xs-12 cover-box padding-0">
                      <div class="col-xs-4">
                        <label class="checkbox-inline"><input type="checkbox" value="" class="check-card check-item check-item-card" disabled>500000 VND</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="500000" type="number" class="form-control check-card input-value-card validate-input-int validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                        <div class="form-group">
                          <label>Quantity</label>
                          <input data-amount="500000" type="number" class="form-control check-card input-quantity-card" value="" disabled/>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <label class="checkbox-inline"><input type="checkbox" value="" class="check-card check-item check-item-card" disabled>200000 VND</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="200000" type="number" class="form-control check-card input-value-card validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                        <div class="form-group">
                          <label>Quantity</label>
                          <input data-amount="200000" type="number" class="form-control check-card input-quantity-card" value="" disabled/>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <label class="checkbox-inline"><input type="checkbox" value="" class="check-card check-item check-item-card" disabled>100000 VND</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="100000" type="number" class="form-control check-card input-value-card validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                        <div class="form-group">
                          <label>Quantity</label>
                          <input data-amount="100000" type="number" class="form-control check-card input-quantity-card" value="" disabled/>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <label class="checkbox-inline"><input type="checkbox" value="" class="check-card check-item check-item-card" disabled>50000 VND</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="50000" type="number" class="form-control check-card input-value-card validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                        <div class="form-group">
                          <label>Quantity</label>
                          <input data-amount="50000" type="number" class="form-control check-card input-quantity-card" value="" disabled/>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <label class="checkbox-inline"><input type="checkbox" value="" class="check-card check-item check-item-card" disabled>20000 VND</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="20000" type="number" class="form-control check-card input-value-card validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                        <div class="form-group">
                          <label>Quantity</label>
                          <input data-amount="20000" type="number" class="form-control check-card input-quantity-card" value="" disabled/>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <label class="checkbox-inline"><input type="checkbox" value="" class="check-card check-item check-item-card" disabled>10000 VND</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="10000" type="number" class="form-control check-card input-value-card validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                        <div class="form-group">
                          <label>Quantity</label>
                          <input data-amount="10000" type="number" class="form-control check-card input-quantity-card" value="" disabled/>
                        </div>
                      </div>
                    </div>
                    <!-- end Card -->
                    <!-- Ncoin -->
                    <div class="col-xs-12 padding-0 margin-top-20">
                      <label class="checkbox-inline"><input type="checkbox" id="check_ncoin" value="">Ncoin</label>
                      <div class="form-group">
                        <div class="form-inline">
                          <label for="total_percentage_ncoin">Total Percentage of Ncoin:</label>
                          <div class='input-group'>
                              <input type="number" class="form-control input-total-percent-ncoin validate-input-int" value="" disabled/>
                              <span class="input-group-addon">
                                  <span class="fa fa-percent"></span>
                              </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div id="box_ncoin" class="col-xs-12 cover-box padding-0 disabled-action">
                      <div class="col-xs-8" style="margin-top: 20px;">
                        <div class="input-group">
                          <input id="ncoin_name" type="number" class="form-control check-ncoin check-item-ncoin validate-input-int" placeholder="Value ..." disabled>
                          <span class="input-group-btn">
                            <button class="btn check-ncoin check-item-ncoin btn-add-item-ncoin" type="button" disabled>Add</button>
                          </span>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>100000000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="100000000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>50000000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="50000000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>20000000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="20000000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>10000000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="10000000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>5000000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="5000000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>2000000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="2000000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>1000000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="1000000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>500000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="500000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>200000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="200000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-4">
                        <a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>
                        <label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="" disabled>100000</label>
                        <div class="form-group">
                            <div class='input-group'>
                                <input data-amount="100000" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>
                                <span class="input-group-addon">
                                    <span class="fa fa-percent"></span>
                                </span>
                            </div>
                        </div>
                      </div>
                    </div>
                    <!-- end Ncoin -->
                    <!-- special gift -->
                    <div class="col-xs-12 padding-0 margin-top-20">
                      <label class="checkbox-inline"><input type="checkbox" value="" id="check_special">Special Gift</label>
                      <div class="col-xs-12 cover-box">
                        <div class="form-group">
                          <label for="name_special_gift">Name:</label>
                          <input type="text" class="form-control" id="name_special_gift" disabled>
                        </div>
                        <div class="form-group">
                          <label for="quantity_special_gift">Quantity:</label>
                          <input type="number" class="form-control" id="quantity_special_gift" disabled>
                        </div>
                        <div class="form-group">
                          <label for="total_percentage_special">Total Percentage of Special:</label>
                          <div class='input-group'>
                            <input type="number" class="form-control input-total-percent-special" value="" disabled/>
                            <span class="input-group-addon">
                              <span class="fa fa-percent"></span>
                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!-- end special gift -->
                    <div class="col-xs-12 padding-0 margin-top-20">
                      <p>Total Percentage of Rewards: <span class="total-percent" style="color: green;">0</span>%<em> (total must be 100%)</em></p>
                    </div>
                  </div>
                </div>
                <!-- end right content -->
                <div class="col-xs-12 padding-0 text-center margin-top-20">
                  <a class="btn btn-info btn-add-event">Create</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="scrolltop" style="display: none;">
        <a href="#"></a>
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <#include "footer.ftl">

</div>
<!-- ./wrapper -->
<#include "script.ftl">
<script src="${root_url}custom/js/eventPage.js"></script>
</body>
</html>
