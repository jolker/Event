var _action_url = "/gift";
var total = $('.totalPage').val();

$(document).ready(function() {
  $('li.gift-manager').addClass('active');

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
