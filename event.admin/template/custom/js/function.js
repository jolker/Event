jQuery(function() {
  $(window).bind("scroll", function() {
    if ($(this).scrollTop() > 100) {
        $("#scrolltop, #clickhere").fadeIn();
    } else {
        $("#scrolltop, #clickhere").fadeOut();
    }
  });

  $('#scrolltop').click(function(){
    $("html, body").animate({ scrollTop: 0 }, 500);
    return false;
  });

  $( ".event-luckybox-collapsed" ).trigger( "click" );

});
