var _action_url = "/event"

$(document).ready(function() {
  $('li.event-manager').addClass('active');

  // input only integer
  $(".validate-input-int").keypress(function (e) {
     //if the letter is not digit then display error and don't type anything
    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
      //display error message
      alert("number only!")
      return false;
    }
  });

  $('#event_time').daterangepicker({
    "timePicker": true,
    "timePicker24Hour": true,
    locale: {
      format: 'DD/MM/YYYY HH:mm'
    }
  }, function(start, end, label) {
    $('#start_time').val(start.format('YYYY-MM-DDTHH:mm:ss'));
    $('#end_time').val(end.format('YYYY-MM-DDTHH:mm:ss'));
  });

  $("#check_card").click(function() {
    if (this.checked) {
      $("input.check-item-card").removeAttr("disabled");
      $("input.input-total-percent-card").removeAttr("disabled");
    } else {
      $("input.check-card").val("");
      $("input.check-card").attr("disabled", true);
      $("input.check-item-card").removeAttr("checked");
      $("input.input-total-percent-card").attr("disabled", true);
      $("input.input-total-percent-card").val("");
    }
  });

  $("#check_ncoin").click(function() {
    if (this.checked) {
      $(".check-item-ncoin").removeAttr("disabled");
      $("#box_ncoin").removeClass("disabled-action");
      $("input.input-total-percent-ncoin").removeAttr("disabled");
    } else {
      $("#box_ncoin").addClass("disabled-action");
      $(".check-ncoin").val("");
      $(".check-ncoin").attr("disabled", true);
      $(".check-item-ncoin").removeAttr("checked");
      $("input.input-total-percent-ncoin").attr("disabled", true);
      $("input.input-total-percent-ncoin").val("");
    }
  });

  $("#check_special").click(function() {
    if (this.checked) {
      $("input.input-total-percent-special").removeAttr("disabled");
      $("input#name_special_gift").removeAttr("disabled");
      $("input#quantity_special_gift").removeAttr("disabled");
    } else {
      $("input.input-total-percent-special").attr("disabled", true);
      $("input.input-total-percent-special").val("");
      $("input#name_special_gift").attr("disabled", true);
      $("input#name_special_gift").val("");
      $("input#quantity_special_gift").attr("disabled", true);
      $("input#quantity_special_gift").val("");
    }
  });

  $(document).on("click",".check-item",function(){
    if (this.checked) {
      $(this).parent().next().find('input').removeAttr("disabled");
      $(this).parent().next().next().find('input').removeAttr("disabled");
    } else {
      $(this).parent().next().find('input').val("");
      $(this).parent().next().next().find('input').val("");
      $(this).parent().next().find('input').attr("disabled", true);
      $(this).parent().next().next().find('input').attr("disabled", true);
    }
  });

  $(document).on("click","input:radio",function(){
    if ($(this).val() == "limit") {
      $('#max_giftbox').removeAttr("disabled");
    } else {
      $('#max_giftbox').attr('value', '');
      $('#max_giftbox').attr("disabled", true);
      $('#max_giftbox').val('');
    }
  });

  $(".btn-add-item-ncoin").click(function() {
    var value_ncoin = $.trim($('#ncoin_name').val());
    var check_duplicate = false;

    if (value_ncoin == '') {
      alert('Please enter value ncoin');
    } else {
      $('.input-value-ncoin').each(function() {
        if (this.id == value_ncoin) {
          check_duplicate = true;
        }
      });
      if (check_duplicate) {
        alert('Value has been existed');
      } else {
        var content_append = '<div class="col-xs-4">' +
          '<a class="pull-right delete-value" style="margin-top:1px;"><i class="fa fa-trash" aria-hidden="true"></i></a>' +
          '<label class="checkbox-inline"><input type="checkbox" class="check-ncoin check-item check-item-ncoin" value="">' + value_ncoin + '</label>' +
          '<div class="form-group">' +
              '<div class="input-group">' +
                  '<input data-amount="' + value_ncoin + '" type="number" class="form-control check-ncoin input-value-ncoin validate-input-int" value="" disabled/>' +
                  '<span class="input-group-addon">' +
                      '<span class="fa fa-percent"></span>' +
                  '</span>' +
              '</div>' +
          '</div>' +
        '</div>'
        $("#box_ncoin").append(content_append);
      }
    }
  });

  $(document).on("click",".delete-value",function(){
    if (confirm('Are you sure to delete this item?')) {
      $(this).parent().remove();
    }
  });

  $(document).on("change",".input-total-percent-card",function(){
    var num1 = parseFloat($("input.input-total-percent-ncoin").val()) || 0;
    var num2 = parseFloat($("input.input-total-percent-card").val()) || 0;
    var num3 = parseFloat($("input.input-total-percent-special").val()) || 0;
    var total = 0;
    total = num1 + num2 + num3;
    $('.total-percent').text(total);
  });

  $(document).on("change",".input-total-percent-ncoin",function(){
    var num1 = parseFloat($("input.input-total-percent-ncoin").val()) || 0;
    var num2 = parseFloat($("input.input-total-percent-card").val()) || 0;
    var num3 = parseFloat($("input.input-total-percent-special").val()) || 0;
    var total = 0;
    total = num1 + num2 + num3;
    $('.total-percent').text(total);
  });

  $(document).on("change",".input-total-percent-special",function(){
    var num1 = parseFloat($("input.input-total-percent-ncoin").val()) || 0;
    var num2 = parseFloat($("input.input-total-percent-card").val()) || 0;
    var num3 = parseFloat($("input.input-total-percent-special").val()) || 0;
    var total = 0;
    total = num1 + num2 + num3;
    $('.total-percent').text(total);
  });

  // send params to event controller
  // create
  $(".btn-add-event").click(function() {
    if($('#name').val() == '') {
      alert("Please enter name");
      return false;
    }

    if($('#money').val() == '') {
      alert("Please enter money");
      return false;
    }

    if($('#point_money').val() == '') {
      alert("Please enter point exchange");
      return false;
    }

    if($('#point_giftbox').val() == '') {
      alert("Please enter point exchange");
      return false;
    }

    if($('#max_giftbox').val() == '') {
      if($('input[name=optradio]:checked').val() == 'limit') {
        alert("Please enter max giftbox");
        return false;
      }
    }

    var check_ncoin_or_card = 0;
    if(!$('#check_card').prop('checked')) {
      check_ncoin_or_card = check_ncoin_or_card + 1;
    }

    if(!$('#check_ncoin').prop('checked')) {
      check_ncoin_or_card = check_ncoin_or_card + 1;
    }

    if(!$('#check_special').prop('checked')) {
      check_ncoin_or_card = check_ncoin_or_card + 1;
    }

    if(check_ncoin_or_card == 3) {
      alert("Please select Card or Ncoin or Special Gift");
      return false;
    }

    if($('#check_card').prop('checked')) {

      if(!$("input.input-total-percent-card").val()){
        alert("Please enter total percentage card");
        return false;
      }

      var check_checked_card = false;
      $('.check-item-card').each(function() {
        if(this.checked){
          check_checked_card = true;
        }
      });

      if(check_checked_card) {
        var check_input_card = false;
        $('.check-item-card').each(function() {
          if(this.checked){
            if(!$(this).parent().next().find('input').val()) {
              check_input_card = true;
            }
          }
        });
        if(check_input_card) {
          alert('Please enter value for item');
          return false;
        }

        var check_input_quantity = false;
        $('.check-item-card').each(function() {
          if(this.checked){
            if(!$(this).parent().next().next().find('input').val()) {
              check_input_quantity = true;
            }
          }
        });
        if(check_input_quantity) {
          alert('Please enter quantity for item');
          return false;
        }

        var check_total_percent_card = 0;
        $('.check-item-card').each(function() {
          if(this.checked){
            check_total_percent_card = check_total_percent_card + parseFloat($(this).parent().next().find('input').val());
          }
        });
        if(check_total_percent_card.toFixed(2) != 100) {
          alert('Total percentage card must be 100%');
          return false;
        }
      } else {
        alert('Please select at least one item');
        return false;
      }
    }

    if($('#check_ncoin').prop('checked')) {

      if(!$("input.input-total-percent-ncoin").val()){
        alert("Please enter total percentage ncoin");
        return false;
      }

      var check_checked_ncoin = false;
      $('.check-item-ncoin').each(function() {
        if(this.checked){
          check_checked_ncoin = true;
        }
      });

      if(check_checked_ncoin) {
        var check_input_ncoin = false;
        $('.check-item-ncoin').each(function() {
          if(this.checked){
            if(!$(this).parent().next().find('input').val()) {
              check_input_ncoin = true;
            }
          }
        });
        if(check_input_ncoin) {
          alert('Please enter value for item');
          return false;
        }

        var check_total_percent_ncoin = 0;
        $('.check-item-ncoin').each(function() {
          if(this.checked){
            check_total_percent_ncoin = check_total_percent_ncoin + parseFloat($(this).parent().next().find('input').val());
          }
        });
        if(check_total_percent_ncoin.toFixed(2) != 100) {
          alert('Total percentage ncoin must be 100%');
          return false;
        }
      } else {
        alert('Please select at least one item');
        return false;
      }
    }

    if($('#check_special').prop('checked')) {
      if(!$("input.input-total-percent-special").val()){
        alert("Please enter total percentage special gift");
        return false;
      }

      if(!$("input#name_special_gift").val()){
        alert("Please enter name of special gift");
        return false;
      }

      if(!$("input#quantity_special_gift").val()){
        alert("Please enter quantity of special gift");
        return false;
      }
    }

    var num1 = parseFloat($("input.input-total-percent-ncoin").val()) || 0;
    var num2 = parseFloat($("input.input-total-percent-card").val()) || 0;
    var num3 = parseFloat($("input.input-total-percent-special").val()) || 0;

    if((num1 + num2 + num3).toFixed(2) != 100) {
      alert('Total percentage must be 100%');
      return false;
    }

    var giftCard = createGiftJSON('.check-item-card', 'CARD');
    var giftNcoin = createGiftJSON('.check-item-ncoin', 'NCOIN');
    var giftSpecial = createSpecialGiftJSON('SPECIAL');

    var datas = {
      "action": "add",
      "name": $('#name').val(),
      "start_time": $('#start_time').val(),
      "end_time": $('#end_time').val(),
      "status": $('#status :selected').val(),
      "money": $('#money').val(),
      "point_money": $('#point_money').val(),
      "point_giftbox": $('#point_giftbox').val(),
      "max_giftbox": $('#max_giftbox').val(),
      "gifts_card_percent": num2,
      "gifts_ncoin_percent": num1,
      "gifts_special_percent": num3,
      "gifts_card": giftCard,
      "gifts_ncoin": giftNcoin,
      "gifts_special": giftSpecial
    }

    swal({
      title: "",
      text: "Confirm new event creation",
      type: "warning",
      showCancelButton: true,
      confirmButtonColor: "#0083B4",
      confirmButtonText: "OK",
      closeOnConfirm: false
    }, function() {
      $.ajax({
        url: _action_url,
        data: datas,
        success: function(response) {
          if (response.result) {
            window.location.href = "/home";
          }
        }
      });
    });

  });
});

function createGiftJSON(type, typeStr) {
  jsonObj = [];
  $(type).each(function() {
    if(this.checked){
      var amount = $(this).parent().next().find('input').data("amount");
      var percentage = $(this).parent().next().find('input').val();
      var quanlity = $(this).parent().next().next().find('input').val();

      item = {}
      item ["type"] = typeStr;
      item ["status"] = "ACTIVED";
      item ["amount"] = amount;
      item ["percentage"] = percentage;
      item ["quanlity"] = quanlity;

      jsonObj.push(item);
    }
  });
  return JSON.stringify(jsonObj);
}

function createSpecialGiftJSON(typeStr) {
  jsonObj = [];
  var name = $('#name_special_gift').val();
  var quantity = $('#quantity_special_gift').val();

  if(name != "") {
    item = {}
    item ["type"] = typeStr;
    item ["status"] = "ACTIVED";
    item ["description"] = name;
    item ["quanlity"] = quantity;

    jsonObj.push(item);
  }

  return JSON.stringify(jsonObj);
}
