$(document).ready(function() {
  $('li.event-manager').addClass('active');

  var total = 0;
  total = parseFloat($('.percent-card').text()) + parseFloat($('.percent-ncoin').text()) + parseFloat($('.percent-special').text());
  $('.total-percent').text(total.toFixed(2));
});
