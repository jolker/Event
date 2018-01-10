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
                                <div class="col-xs-8">
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
                      <label for="status">Status:</label>
                    </div>
                    <div class="col-xs-10">
                      <select class="form-control" id="status">
                        <option value="PENDING">PENDING</option>
                        <option value="ACTIVE">ACTIVE</option>
                      </select>
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
                                    <div class="col-xs-12 margin-top-20">
                                        <label for="content">Content:</label>
                                    </div>
                                    <div class="col-xs-12 padding-0 cover-box" id="content">
                                        <div class="col-xs-12 padding-0 margin-top-20">
                                            <div class="col-xs-10 padding-0">
                                            </div>
                                            <div class="col-xs-2 " style="text-align:right">
                                                <button type="button" class="btn btn-success" id="plus" ><b>+</b></button>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 padding-0 margin-top-20">
                                                    <div class="col-xs-1 padding-0" style="padding-bottom:10px;text-align:right">

                                                    </div> 
                                                    <div class="col-xs-2 padding-0"  style="text-align:right;">
                                                        Accure 
                                                    </div>
                                                    <div class="col-xs-2" style="padding-left:5px">
                                                        <input class="form-check-input" type="checkbox" id="gifts_limit" style="width:20px;height:20px" >
                                                    </div>
                                        </div>
                                        <div id="duplicetor">
                                            <div class="col-xs-12 padding-0 margin-top-20 " id="duplicetor0">
                                                <div class="col-xs-12">
                                                   
                                                    <div class="col-xs-3 padding-0"  style="text-align:right;padding-right:5px">Milestone Reward
                                                    </div>
                                                    <div class="col-xs-2 padding-0">
                                                        <input type="text" class="form-control"  name="number-input-milestone"  onchange="commaSeparateNumber(this)" onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
                                                    </div>
                                                    <div class="col-xs-2 padding-0" style="text-align:right;padding-right:5px">Reward 
                                                    </div>
                                                    <div class="col-xs-2 padding-0">
                                                        <input type="text" class="form-control " name="number-input-reward"  onchange="commaSeparateNumber(this)" onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
                                                    </div>
                                                    <div class="col-xs-1 padding-0  btn-remove-content" style="padding-bottom:10px;text-align:right">
                                                        <button type="button" class="btn btn-danger remove"  ><b>-</b></button>
                                                    </div> 
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                    <div class="col-xs-12 padding-0 margin-top-20">
                                                    <div class="col-xs-1 padding-0" style="padding-bottom:10px;text-align:right">

                                                    </div> 
                                                    <div class="col-xs-2 padding-0"  style="text-align:right;">
                                                       
                                                    </div>
                                                    <div class="col-xs-12" style="padding-left:5px;padding-bottom:10px;text-align:center">
                                                        <button type="button" class="btn btn-info btn-view" id="save" >save</button>
                                                    </div>
                                        </div>
                                </div>
                                  <!-- end left content -->
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
                    <script src="${root_url}custom/js/eventPointPage.js"></script>
                    </body>
                    </html>
