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
        <a class="btn btn-primary" href='/event'><i class="fa fa-plus-circle" aria-hidden="true"></i> Create Event</a>
        <div class="container-body">
          <h4 class="text-center"><strong><em>----- List Event -----</em></strong></h4>
          <div class="table-responsive">
            <table class="table table-hover table-striped text-center table-bordered">
              <thead>
                <tr class="info">
                  <th class="text-center">Name</th>
                  <th class="text-center">Start Date</th>
                  <th class="text-center">End Date</th>
                  <th class="text-center">Status</th>
                  <th></th>
                  <th></th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <#if list_event??>
								 <#if list_event?has_content>
								  <#list list_event as event>
									 <tr>
										<td>${event.eventName}</td>
										<td>${event.startDate}</td>
									  <td>${event.endDate}</td>
									  <td>${event.status}</td>
                    <td><button type="button" class="btn btn-info btn-view" data-id="${event.id}"><i class="fa fa-file-text-o" aria-hidden="true"></i> View</button></td>
                    <td><button type="button" class="btn btn-success btn-edit" data-id="${event.id}"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</button></td>
                    <td><button type="button" class="btn btn-warning btn-duplicate" data-id="${event.id}"><i class="fa fa-files-o" aria-hidden="true"></i> Clone</button></td>
									 </tr>
									</#list>
								 <#else>
									 <tr>
										<td></td>
										<td></td>
										<td></td>
                    <td>No Result</td>
										<td></td>
										<td></td>
										<td></td>
									 </tr>
								 </#if>
								</#if>
              </tbody>
            </table>
          </div>
          <input type="hidden" value="${total_event}" class="totalPage">
        </div>
        <div class="text-center">
          <ul class="pagination-sm" id="pagination"></ul>
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
<script src="${root_url}custom/js/homePage.js"></script>
</body>
</html>
