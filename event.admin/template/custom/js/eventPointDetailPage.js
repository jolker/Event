/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    $("#status").val($('#status_server').val());
  var arrStart = $('#start_time').val().split("T");
  var arrStartDate=arrStart[0].split("-");
  var arrStartTime=arrStart[1].split(":");
//  var startTime = arrStart[0] + " " + arrStart[1].split(":")[0] + ":" + arrStart[1].split(":")[1];
  var   startTime=arrStartDate[2]+"/"+arrStartDate[1]+"/"+arrStartDate[0]+" "+arrStartTime[0]+arrStartTime[1];
  var arrEnd = $('#end_time').val().split("T");
  var arrEndDate=arrEnd[0].split("-");
  var arrEndTime=arrEnd[1].split(":");
//    var endTime = arrEnd[0] + " " + arrEnd[1].split(":")[0] + ":" + arrEnd[1].split(":")[1];
  var endTime=arrEndDate[2]+"/"+arrEndDate[1]+"/"+arrEndDate[0]+" "+arrEndTime[0]+arrEndTime[1];
$('#event_time').daterangepicker({
    "timePicker": true,
    "timePicker24Hour": true,
    startDate: startTime,
    endDate: endTime,
    locale: {
      format: 'DD/MM/YYYY HH:mm'
    }
  }, function(start, end, label) {
    $('#start_time').val(start.format('YYYY-MM-DDTHH:mm:ss'));
    $('#end_time').val(end.format('YYYY-MM-DDTHH:mm:ss'));
  });
  var inp = document.getElementsByTagName('input');
    var checkData=true;
    for (var i in inp) {
        if (inp[i].type === "text" && inp[i].name==="number-input-milestone" || inp[i].name==="number-input-reward" ) {
        inp.value=addCommas(inp.value);
        }
    }
  
});
$(document).on("click", "#plus", function () {
    var gen=10000;
    if (row <= 4) {
        
        var content_append = '<div class="col-xs-12 padding-0 margin-top-20" id="duplicetor' + gen + '">' +
                '<div class="col-xs-12">' +
                '<div class="col-xs-3 padding-0"  style="text-align:right;padding-right:5px">Milestone Reward' +
                '</div>' +
                '<div class="col-xs-2 padding-0">' +
                ' <input type="text" class="form-control" name="number-input-milestone"  onchange="commaSeparateNumber(this)" onkeypress="return event.charCode >= 48 && event.charCode <= 57">' +
                '</div>' +
                '<div class="col-xs-2 padding-0" style="text-align:right;padding-right:5px">Reward' +
                '</div>' +
                '<div class="col-xs-2 padding-0">' +
                '<input type="text" class="form-control" name="number-input-reward" onchange="commaSeparateNumber(this)" onkeypress="return event.charCode >= 48 && event.charCode <= 57">' +
                '</div>' +
                '<div class="col-xs-1 padding-0  btn-remove-content" style="padding-bottom:10px;text-align:right">' +
                '<button type="button" class="btn btn-danger remove" value="' + gen + '"><b>-</b></button>' +
                '</div>' +
                '</div>' +
                '</div>';
        $("#duplicetor").append(content_append);
        row = row + 1;
        gen=gen+1;
    }else{
        alert("Reward can not greater than 5!")
    }
});
$(document).on("click", ".remove", function () {
//    var element = document.getElementById("duplicetor"+i);
    var targetId = $(this).closest('div[id^=duplicetor]')[0].id;
    if(row===1){
    alert("Reward must have at least one");
    }
    if (row > 1) {
        if (confirm('Are you sure to delete this item?')) {
            var element = document.getElementById(targetId);
            element.parentNode.removeChild(element);
            row = row - 1;
        }
    }
    
});

function commaSeparateNumber(val) {
    val.value = addCommas(removeCommas(val.value));
}
//
//$(document).on("click",".remove",function(){
//    alert($(this).value)
//    if (confirm('Are you sure to delete this item?')) {
//      $(this).parent().remove();
//    }
//  });
function addCommas(nStr)
{
    nStr += '';
    var x = nStr.split('.');
    var x1 = x[0];
    var x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}
function removeCommas(str) {
    while (str.search(",") >= 0) {
        str = (str + "").replace(',', '');
    }
    return str;
};
$(document).on("click", "#update", function () {
    var inp = document.getElementsByTagName('input');
    var checkData=true;
    for (var i in inp) {
        if (inp[i].type === "text" && inp[i].name!=="daterangepicker_start" && inp[i].name!=="daterangepicker_end" ) {
            if (inp[i].value === "") {
                alert('Invalid value detected');
                inp[i].focus();
                checkData=false;
                break;
            }
        }
    }
    if(checkData){
    var gifts_limit = false;
    if (document.getElementById("gifts_limit").checked) {
        gifts_limit = true;
    }
    var milestone = [];
    var reward = [];
    for (var i in inp) {
        if (inp[i].type === "text" && inp[i].name === "number-input-milestone") {
            milestone.push(inp[i].value);
        }
        if (inp[i].type === "text" && inp[i].name === "number-input-reward") {
            reward.push(inp[i].value);
        }
    }
    milestone.pop();
    reward.pop();
    var a="";
    if(milestone.length===1){
        a=a+removeCommas(milestone[0])+":"+removeCommas(reward[0]);
    }else{
   for(n=0;n<milestone.length;n++){
       a=a+removeCommas(milestone[n])+":"+removeCommas(reward[n]);
       if(n<milestone.length-1 ){
         a=a+",";
     }
        }
    }
    a="{"+a+"}";
    var datas = {
        "action": "update",
        "id": id,
        "name": $('#name').val(),
        "start_date": $('#start_time').val(),
        "type": "accrue",
        "status": $('#status').val(),
        "end_date": $('#end_time').val(),
        "reward": a,
        "gifts_limit": gifts_limit
    };
     $.ajax({
        url: "/eventpoint",
        data: datas,
        success: function(response) {
          if (response.result) {
            window.location.href = "/homepoint";
          }
        }
//        ,error: function (xhr) { console.log(xhr); } 
      });
    }
});
$(document).on("click", "#duplicate", function () {
    var inp = document.getElementsByTagName('input');
    var checkData=true;
    for (var i in inp) {
        if (inp[i].type === "text" && inp[i].name!=="daterangepicker_start" && inp[i].name!=="daterangepicker_end" ) {
            if (inp[i].value === "") {
                alert('Invalid value detected');
                inp[i].focus();
                checkData=false;
                break;
            }
        }
    }
    if(checkData){
    var gifts_limit = false;
    if (document.getElementById("gifts_limit").checked) {
        gifts_limit = true;
    }
    var milestone = [];
    var reward = [];
    for (var i in inp) {
        if (inp[i].type === "text" && inp[i].name === "number-input-milestone") {
            milestone.push(inp[i].value);
        }
        if (inp[i].type === "text" && inp[i].name === "number-input-reward") {
            reward.push(inp[i].value);
        }
    }
    milestone.pop();
    reward.pop();
    var a="";
    if(milestone.length===1){
        a=a+removeCommas(milestone[0])+":"+removeCommas(reward[0]);
    }else{
   for(n=0;n<milestone.length;n++){
       a=a+removeCommas(milestone[n])+":"+removeCommas(reward[n]);
       if(n<milestone.length-1 ){
         a=a+",";
     }
        }
    }
    a="{"+a+"}";
    var datas = {
        "action": "duplicate",
        "name": $('#name').val(),
        "start_date": $('#start_time').val(),
        "type": "accrue",
        "status": $('#status').val(),
        "end_date": $('#end_time').val(),
        "reward": a,
        "gifts_limit": gifts_limit
    };
     $.ajax({
        url: "/eventpoint",
        data: datas,
        success: function(response) {
          if (response.result) {
            window.location.href = "/homepoint";
          }
        }
//        ,error: function (xhr) { console.log(xhr); } 
      });
    }
});

function createPointJSON(type, typeStr) {
    jsonObj = [];
    $(type).each(function () {
        if (this.checked) {
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
