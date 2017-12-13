var nav = $('#barraup');
$('.zoom').zoomy({
    innerZoom: true,
    lensHeight: 100,
    lensWidth: 300
});

$('#thumbs img').click(function () {
    $('#largeImage').attr('src', $(this).attr('src'));
    $('#description').html($(this).attr('alt'));
});
$('#thumbs').delegate('img', 'click', function () {
    $('#largeImage').attr('src', $(this).attr('src').replace('thumbs', 'largeImage'));
    $('#description').html($(this).attr('alt'));
});

if (nav.length != 0) {
    var height = $("#navpricipal").height();
    var navHomeY = nav.offset().top - height;
    var isFixed = false;

    $(document).scroll(function () {


        var $w = $(window);
        var scrollTop = $w.scrollTop();
        var shouldBeFixed = scrollTop > navHomeY;
        if (shouldBeFixed) {
            if (!isFixed) {
                nav.css({
                    position: 'fixed',
                    top: height,
                });
                $('#barraup').load();
                $('#barraup').css("z-index:1000;");
                $('#barraup').addClass("fixed transbg");

                isFixed = true;
            }
        } else if (isFixed)
        {
            $('#barraup').removeClass("fixed transbg");
            nav.css({
                position: 'static'
            });
            isFixed = false;
        }

    });
}
$('.ir-arriba').click(function () {
    $('body, html').animate({
        scrollTop: '0px'
    }, 300);
});

$(window).scroll(function () {
    if ($(this).scrollTop() > 0) {
        $('.ir-arriba').slideDown(300);
    } else {
        $('.ir-arriba').slideUp(300);
    }
});

/*menu*/
function calculateScroll() {
    var contentTop = [];
    var contentBottom = [];
    var winTop = $(window).scrollTop();
    var rangeTop = 200;
    var rangeBottom = 500;
    $('.navmenu').find('li >a').each(function () {
        contentTop.push($($(this).attr('href')).offset().top);
        contentBottom.push($($(this).attr('href')).offset().top + $($(this).attr('href')).height());
    })
    $.each(contentTop, function (i) {
        if (winTop > contentTop[i] - rangeTop && winTop < contentBottom[i] - rangeBottom) {
            $('.navmenu li')
                    .removeClass('active')
                    .eq(i).addClass('active');
        }
    })

}
;

$(document).ready(function () {
    calculateScroll();
    $(window).scroll(function (event) {
        calculateScroll();
    });
    $('.navmenu ul li a').click(function () {
        $('html, body').animate({scrollTop: $(this.hash).offset().top - 80}, 800);
        return false;
    });
});

