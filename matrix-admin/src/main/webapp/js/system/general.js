$(function () {
    ///// SHOW/HIDE USERDATA WHEN USERINFO IS CLICKED /////

    $('.userinfo').click(function () {
        if (!$(this).hasClass('active')) {
            $('.userinfodrop').show();
            $(this).addClass('active');
        } else {
            $('.userinfodrop').hide();
            $(this).removeClass('active');
        }
        //remove notification box if visible
        $('.notification').removeClass('active');
        $('.noticontent').remove();

        return false;
    });


    ///// SHOW/HIDE NOTIFICATION /////

    $('.notification a').click(function () {
        var t = $(this);
        var url = t.attr('href');
        if (!$('.noticontent').is(':visible')) {
            $.post(url, function (data) {
                t.parent().append('<div class="noticontent">' + data + '</div>');
            });
            //this will hide user info drop down when visible
            $('.userinfo').removeClass('active');
            $('.userinfodrop').hide();
        } else {
            t.parent().removeClass('active');
            $('.noticontent').hide();
        }
        return false;
    });


    ///// SHOW/HIDE BOTH NOTIFICATION & USERINFO WHEN CLICKED OUTSIDE OF THIS ELEMENT /////


    $(document).click(function (event) {
        var ud = $('.userinfodrop');
        var nb = $('.noticontent');

        //hide user drop menu when clicked outside of this element
        if (!$(event.target).is('.userinfodrop')
            && !$(event.target).is('.userdata')
            && ud.is(':visible')) {
            ud.hide();
            $('.userinfo').removeClass('active');
        }

        //hide notification box when clicked outside of this element
        if (!$(event.target).is('.noticontent') && nb.is(':visible')) {
            nb.remove();
            $('.notification').removeClass('active');
        }
    });


    ///// NOTIFICATION CONTENT /////              current

    $('.notitab a').on('click', function () {
        var id = $(this).attr('href');
        $('.notitab li').removeClass('current'); //reset current
        $(this).parent().addClass('current');
        if (id == '#messages')
            $('#activities').hide();
        else
            $('#messages').hide();

        $(id).show();
        return false;
    });



    // 切换导航栏的时候 这里的菜单来应该默认打开 从而配合【Plus】按钮进行菜单的全部打开或者全部收起
    $("#menu-open-close").click(function () {
        var isOpen_ = false; // 是否全部打开
        var eleList = $(".nav ul ul");
        for (var i = 0; i < eleList.length; i++) {
            var ele = eleList[i];
            if ($(ele).css("display") == 'none') {
                isOpen_ = true;
                break;
            }
        }
        if(isOpen_ ){
            eleList.css('display' , 'block');
        }else{
            eleList.css('display' , 'none');
        }

    })


    /**
     * SHOW/HIDE SUB MENU WHEN MENU COLLAPSED
     * 显示/隐藏菜单倒塌时子菜单
     */
    $('.menucoll > ul > li, .menucoll2 > ul > li').on('mouseenter mouseleave', function (e) {
        if (e.type == 'mouseenter') {
            $(this).addClass('hover');
            $(this).find('ul').show();
        } else {
            $(this).removeClass('hover').find('ul').hide();
        }
    });





    ///// RESPONSIVE /////
    if ($(document).width() < 640) {
        $('.togglemenu').addClass('togglemenu_collapsed');
        if ($('.vernav').length > 0) {

            $('.iconmenu').addClass('menucoll');
            $('body').addClass('withmenucoll');
            $('.centercontent').css({marginLeft: '56px'});
            if ($('.iconmenu').length == 0) {
                $('.togglemenu').removeClass('togglemenu_collapsed');
            } else {
                $('.iconmenu > ul > li > a').each(function () {
                    var label = $(this).text();
                    $('<li><span>' + label + '</span></li>')
                        .insertBefore($(this).parent().find('ul li:first-child'));
                });
            }

        } else {

            $('.iconmenu').addClass('menucoll2');
            $('body').addClass('withmenucoll2');
            $('.centercontent').css({marginLeft: '36px'});

            $('.iconmenu > ul > li > a').each(function () {
                var label = $(this).text();
                $('<li><span>' + label + '</span></li>')
                    .insertBefore($(this).parent().find('ul li:first-child'));
            });
        }
    }


    ///// ON LOAD WINDOW /////
    function reposSearch() {
        if ($(window).width() < 520) {
            if ($('.searchinner').length == 0) {
                $('.search').wrapInner('<div class="searchinner"></div>');
                $('<a class="searchicon"></a>').insertBefore($('.searchinner'));
                $('<a class="searchcancel"></a>').insertAfter($('.searchinner button'));
            }
        } else {
            if ($('.searchinner').length > 0) {
                $('.search form').unwrap();
                $('.searchicon, .searchcancel').remove();
            }
        }
    }

    reposSearch();

    ///// ON RESIZE WINDOW /////
    $(window).resize(function () {

        if ($(window).width() > 640)
            $('.centercontent').removeAttr('style');

        reposSearch();

    });



});

