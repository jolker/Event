var _action_url = "/home";
var total = $('.totalPage').val();

$(document).ready(function() {
  $('li.event-manager').addClass('active');

  if ((parseInt(total) % 10) != 0 ) {
    total = parseInt(parseInt(total)/10) + 1;
  } else {
    total = parseInt(parseInt(total)/10);
  }

  $('#pagination').twbsPagination({
    totalPages: total,
    visiblePages: 5,
    onPageClick: function(event, page) {
      var datas = {
        "page": page
      }
      $.ajax({
        url: _action_url,
        data: datas,
        success: function(response) {
          var result = $(response).find('.table-responsive');
          $('.table-responsive').replaceWith(result);
        },
        error: function(response) {

        }
      });
    }
  });
});

$(document).on('click', '.btn-view', function(evt){
    var id = $(this).data("id");
    location.href = "/event?action=view&id=" + id;
});

$(document).on('click', '.btn-edit', function(evt){
    var id = $(this).data("id");
    location.href = "/event?action=edit&id=" + id;
});

$(document).on('click', '.btn-duplicate', function(evt){
    var id = $(this).data("id");
    location.href = "/event?action=duplicate&id=" + id;
});

$(document).on('click', '.btn-delete', function(evt){
    var id = $(this).data("id");
    swal({
      title: "",
      text: "Confirm delete event",
      type: "warning",
      showCancelButton: true,
      confirmButtonColor: "#0083B4",
      confirmButtonText: "OK",
      closeOnConfirm: false
    }, function() {
      location.href = "/event?action=delete&id=" + id;
    });
});
