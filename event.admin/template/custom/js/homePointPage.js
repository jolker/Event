var _action_url = "/homepoint";
var total = $('.totalPage').val();

$(document).ready(function() {
  $('li.event-point-manager').addClass('active');

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
    location.href = "/eventpoint?action=view&id=" + id+"&type_button=update";
});



$(document).on('click', '.btn-duplicate', function(evt){
    var id = $(this).data("id");
    location.href = "/eventpoint?action=view&id=" + id+"&type_button=clone";
});


function addRow() {
    var div = document.createElement('div');

    div.className = 'row';

    div.innerHTML = '<input type="text" name="name" value="" />\
        <input type="text" name="value" value="" />\
        <label> <input type="checkbox" name="check" value="1" /> Checked? </label>\
        <input type="button" value="-" onclick="removeRow(this)">';

     document.getElementById('content').appendChild(div);
}

function removeRow(input) {
    document.getElementById('content').removeChild( input.parentNode );
}