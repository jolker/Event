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
              <#if event??>
                <div class="panel-body">
                  <!-- left content -->
                  <div class="col-xs-6">
                    <div class="col-xs-12 padding-0">
                      <div class="col-xs-2">
                        <label for="name">Name:</label>
                      </div>
                      <div class="col-xs-10">
                        <p>${event.eventName}</p>
                      </div>
                    </div>
                    <div class="col-xs-12 padding-0 margin-top-20">
                      <div class="col-xs-3">
                        <label for="time">Start Time:</label>
                      </div>
                      <div class="col-xs-9">
                        <p>${event.startDate}</p>
                      </div>
                    </div>
                    <div class="col-xs-12 padding-0 margin-top-20">
                      <div class="col-xs-3">
                        <label for="time">End Time:</label>
                      </div>
                      <div class="col-xs-9">
                        <p>${event.endDate}</p>
                      </div>
                    </div>
                    <div class="col-xs-12 margin-top-20">
                      <label for="content">Content:</label>
                    </div>
                    <div class="col-xs-12 padding-0 cover-box">
                      <div class="col-xs-12 padding-0 margin-top-20">
                        <div class="col-xs-4 text-center">
                          <em>${event.payMoney} VND</em>
                        </div>
                        <div class="col-xs-4 text-center">
                          <p>=</p>
                        </div>
                        <div class="col-xs-4 text-center">
                          <em>${event.scoreMoney} Point</em>
                        </div>
                      </div>

                      <div class="col-xs-12 padding-0 margin-top-20">
                        <div class="col-xs-4 text-center">
                          <em>${event.scoreGift} Point</em>
                        </div>
                        <div class="col-xs-4 text-center">
                          <p>=</p>
                        </div>
                        <div class="col-xs-4 text-center">
                          <em>1 Gift Box</em>
                        </div>
                      </div>

                      <div class="col-xs-12 padding-0 margin-top-20">
                        <div class="col-xs-6 text-center">
                          <#if event.giftsPerPerson == "0">
                            <label>Unlimit</label>
                          <#else>
                            <label>Limit</label>
                          </#if>
                        </div>
                        <div class="col-xs-6 text-center">
                          <label>${event.giftsPerPerson} Max Gift Box</label>
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
                        <label>Card</label>
                        <p class="pull-right">Total Percentage of Card: <span class="percent-card">${event.giftsCardPercent}</span>%</p>
                      </div>
                      <div class="col-xs-12 cover-box padding-0">
                        <#if list_giftcard??>
  												<#if list_giftcard?has_content>
  													<#list list_giftcard as giftcard>
                              <#if giftcard.status??>
                                <#if giftcard.status == "ACTIVED">
                                  <div class="col-xs-4">
                                    <label>${giftcard.amount} VND</label>
                                    <div class="form-group">
                                        <div class='input-group'>
                                            <input id="${giftcard.amount}" type="number" class="form-control check-card input-value-card" value="${giftcard.percentage}" disabled/>
                                            <span class="input-group-addon">
                                                <span class="fa fa-percent"></span>
                                            </span>
                                        </div>
                                    </div>
                                    <label>Quantity: ${giftcard.quantity}</label>
                                  </div>
                                </#if>
                              </#if>
  													</#list>
  												<#else>
  												</#if>
											  </#if>
                      </div>
                      <!-- end Card -->
                      <!-- Ncoin -->
                      <div class="col-xs-12 padding-0 margin-top-20">
                        <label>Ncoin</label>
                        <p class="pull-right">Total Percentage of Ncoin: <span class="percent-ncoin">${event.giftsNcoinPercent}</span>%</p>
                      </div>
                      <div id="box_ncoin" class="col-xs-12 cover-box padding-0">
                        <#if list_giftncoin??>
  												<#if list_giftncoin?has_content>
  													<#list list_giftncoin as giftncoin>
                              <#if giftncoin.status??>
                                <#if giftncoin.status == "ACTIVED">
                                  <div class="col-xs-4">
                                    <label>${giftncoin.amount}</label>
                                    <div class="form-group">
                                        <div class='input-group'>
                                            <input id="${giftncoin.amount}" type="number" class="form-control check-card input-value-ncoin" value="${giftncoin.percentage}" disabled/>
                                            <span class="input-group-addon">
                                                <span class="fa fa-percent"></span>
                                            </span>
                                        </div>
                                    </div>
                                  </div>
                                </#if>
                              </#if>
  													</#list>
  												<#else>
  												</#if>
											  </#if>
                      </div>
                      <!-- end Ncoin -->
                      <!-- Special -->
                      <div class="col-xs-12 padding-0 margin-top-20">
                        <label>Special Gift</label>
                        <p class="pull-right">Total Percentage of Special Gift: <span class="percent-special">${event.giftsSpecialPercent}</span>%</p>
                      </div>
                      <div class="col-xs-12 cover-box padding-0">
                        <#if list_giftspecial??>
  												<#if list_giftspecial?has_content>
  													<#list list_giftspecial as special>
                              <#if special.status??>
                                <#if special.status == "ACTIVED">
                                  <div class="col-xs-6 text-center">
                                    <label>${special.description}</label>
                                  </div>
                                  <div class="col-xs-6 text-center">
                                    <label>${special.quantity}</label>
                                  </div>
                                </#if>
                              </#if>
  													</#list>
  												<#else>
  												</#if>
											  </#if>
                      </div>
                      <!-- end Special -->
                      <div class="col-xs-12 padding-0 margin-top-20">
                        <p>Total Percentage of Rewards: <span class="total-percent" style="color: green;">0</span>%<em> (total must be 100%)</em></p>
                      </div>
                    </div>
                  </div>
                  <!-- end right content -->
                </div>
              <#else>
                <div class="panel-body">
                  <p><em>${msg}</em></p>
                </div>
              </#if>
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
<script src="${root_url}custom/js/eventDetailPage.js"></script>
</body>
</html>
