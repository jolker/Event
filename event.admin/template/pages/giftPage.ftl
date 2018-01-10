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
        <a href="/home"><span class="glyphicon glyphicon-home"></span> Home</a> > Lost Gift Manager
      </h4>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="container-body">
          <h4 class="text-center"><strong><em>----- List Lost Gift -----</em></strong></h4>
          <div class="table-responsive">
            <table class="table table-hover table-striped text-center table-bordered">
              <thead>
                <tr class="info">
                  <th class="text-center">User Id</th>
                  <th class="text-center">Event Id</th>
                  <th class="text-center">Amount</th>
                  <th class="text-center">Status</th>
                  <th class="text-center">Created At</th>
                  <th class="text-center">Updated At</th>
                </tr>
              </thead>
              <tbody>
                <#if list_gift??>
								 <#if list_gift?has_content>
								  <#list list_gift as gift>
									 <tr>
										<td>${gift.userId}</td>
										<td>${gift.eventId}</td>
									  <td>${gift.amount}</td>
                    <td>${gift.status}</td>
									  <td>${gift.createdAt}</td>
                    <td>${gift.updatedAt}</td>
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
									 </tr>
								 </#if>
								</#if>
              </tbody>
            </table>
          </div>
          <input type="hidden" value="${total_gift}" class="totalPage">
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
<script src="${root_url}custom/js/giftPage.js"></script>
</body>
</html>
