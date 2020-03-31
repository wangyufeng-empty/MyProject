(function ($) {
 "use strict";
 
  /*----------------------------
 price-slider active
------------------------------ */
    var range = $('#slider-range');
    var amount = $('#amount');
    
	  range.slider({
	   range: true,
	   min: 2,
	   max: 300,
	   values: [ 2, 300 ],
	   slide: function( event, ui ) {
		amount.val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
	   }
	  });
	  amount.val( "$" + range.slider( "values", 0 ) +
	   " - $" + range.slider( "values", 1 ) );   		

 /*----------------------------
 jQuery MeanMenu
------------------------------ */
jQuery('#mobile-menu-active').meanmenu();


/*----------------------
	 Carousel Activation
	----------------------*/ 
  $(".let_new_carasel").owlCarousel({
      autoPlay: true, 
	  slideSpeed:2000,
	  pagination:true,
	  navigation:true,	  
      items : 1,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-caret-left'></i>","<i class='fa fa-caret-right'></i>"],
      itemsDesktop : [1199,1],
	  itemsDesktopSmall : [980,1],
	  itemsTablet: [768,1],
	  itemsMobile : [767,1],
  });

 /*----------------------------
		Tooltip
    ------------------------------ */
    $('[data-toggle="tooltip"]').tooltip({
        animated: 'fade',
        placement: 'top',
        container: 'body'
    });
 /*----------------------------
  single portfolio activation
------------------------------ */ 
  $(".sub_pix").owlCarousel({
      autoPlay: true, 
	  slideSpeed:2000,
	  pagination:true,
	  navigation:false,	  
      items : 5,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,4],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,5],
	  itemsMobile : [767,3],
  });
 /*----------------------------
	toggole active
     ------------------------------ */
	$( ".all_catagories" ).on("click", function() {
	  $( ".cat_mega_start" ).slideToggle( "slow" );
	});
	
	$( ".showmore-items" ).on("click", function() {
	  $( ".cost-menu" ).slideToggle( "slow" );
	});


 
/*----------------------
	New  Products Carousel Activation
	----------------------*/ 
  $(".whole_product").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 3,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,3],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,1],
	  itemsMobile : [767,1],
  });

 /*----------------------
	Hot  Deals Carousel Activation
	----------------------*/  
  $(".new_cosmatic").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 1,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,1],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,2],
	  itemsMobile : [767,1],
  });

 /*---------------------
	 countdown
	--------------------- */
		$('[data-countdown]').each(function() {
		  var $this = $(this), finalDate = $(this).data('countdown');
		  $this.countdown(finalDate, function(event) {
			$this.html(event.strftime('<span class="cdown days"><span class="time-count">%-D</span> <p>Days</p></span> <span class="cdown hour"><span class="time-count">%-H</span> <p>Hour</p></span> <span class="cdown minutes"><span class="time-count">%M</span> <p>Min</p></span> <span class="cdown second"> <span><span class="time-count">%S</span> <p>Sec</p></span>'));
		  });
		});

  /*----------------------
        Products Catagory Carousel Activation
	----------------------*/ 
  $(".feature-carousel").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 4,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,3],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,2],
	  itemsMobile : [767,1],
  });

/*----------------------------
   Top Rate Carousel Activation
------------------------------ */  
  $(".all_ayntex").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 1,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,1],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,2],
	  itemsMobile : [767,1],
  });

/*----------------------------
   Featured Catagories Carousel Activation
------------------------------ */ 
  $(".achard_all").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 5,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,4],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,4],
	  itemsMobile : [767,2
],
  });

 /*----------------------------
   Blog Post Carousel Activation
 ------------------------------ */
  $(".blog_carasel").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 3,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,2],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,2],
	  itemsMobile : [767,1],
  });
 /*----------------------------
   Brand Logo Carousel Activation
------------------------------ */  
  $(".all_brand").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 6,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,4],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,2],
	  itemsMobile : [480,2],
  });
/*----------------------
	scrollUp 
	----------------------*/	
	$.scrollUp({
        scrollText: '<i class="fa fa-angle-double-up"></i>',
        easingType: 'linear',
        scrollSpeed: 900,
        animation: 'fade'
    });


/*----------------------
	New  Products home-page-2 Carousel Activation
	----------------------*/  
  $(".product_2").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 4,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,3],
	  itemsDesktopSmall : [980,4],
	  itemsTablet: [768,2],
	  itemsMobile : [767,1],
  });
  /*----------------------------
   Blog Post home-page-2 Carousel Activation
------------------------------ */ 
  $(".blog_new_carasel_2").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 2,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,2],
	  itemsDesktopSmall : [980,2],
	  itemsTablet: [768,1],
	  itemsMobile : [767,1],
  });

 /*----------------------------
   Products Catagory-2 Carousel Activation
------------------------------ */  
  $(".feature-carousel-2").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 2,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,2],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,2],
	  itemsMobile : [767,1],
  });

 /*----------------------------
   Blog Post home-page-3 Carousel Activation
------------------------------ */  
  $(".blog_carasel_5").owlCarousel({
      autoPlay: false, 
	  slideSpeed:2000,
	  pagination:false,
	  navigation:true,	  
      items : 4,
	  /* transitionStyle : "fade", */    /* [This code for animation ] */
	  navigationText:["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
      itemsDesktop : [1199,4],
	  itemsDesktopSmall : [980,3],
	  itemsTablet: [768,2],
	  itemsMobile : [767,1],
  });
/*-----------------------------
	Category Menu toggle
	-------------------------------*/
    $('.expandable a').on('click', function() {
        $(this).parent().find('.category-sub').toggleClass('submenu-active'); 
        $(this).toggleClass('submenu-active');  
        return false;  
    });	
	
/*----------------------------
  MixItUp:
------------------------------ */
	$('#Container') .mixItUp();

 /*----------------------------
 magnificPopup:
------------------------------ */	
	 $('.magnify').magnificPopup({type:'image'});
	 
	 
/*-------------------------
  Create an account toggle function
--------------------------*/
	 $( "#cbox" ).on("click", function() {
        $( "#cbox_info" ).slideToggle(900);
     });
	 
	 
	  $( '#showlogin, #showcoupon' ).on('click', function() {
			 $(this).parent().next().slideToggle(600);
		 }); 
	 
		 /*-------------------------
		   accordion toggle function
		 --------------------------*/
		 $('.payment-accordion').find('.payment-accordion-toggle').on('click', function(){
		   //Expand or collapse this panel
		   $(this).next().slideToggle(500);
		   //Hide the other panels
		   $(".payment-content").not($(this).next()).slideUp(500);
	 
		 });
		 /* -------------------------------------------------------
		  accordion active class for style
		 ----------------------------------------------------------*/
		 $('.payment-accordion-toggle').on('click', function(event) {
			 $(this).siblings('.active').removeClass('active');
			 $(this).addClass('active');
			 event.preventDefault();
		 });
	 
	 
	 
	
  

})(jQuery); 
 
