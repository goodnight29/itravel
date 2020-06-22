initMenuData();

function initMenuData() {
    //发送ajax请求获取当前登录用户的菜单数据
    $.ajax({
        url:"/permissions/current",
        type:"POST",
        async:false,
        success:function (data) {

            //1）取到menu
            var menu = $("#menu");

            //2)遍历data，取出菜单数据，进行绑定
            $.each(data, function (index, item) {
                //创建<a>
                var a = $("<a href='javascript:;'></a>");

                var css = item.css;
                if (css!=null && css!=""){
                    a.append("<i aria-hidden='true' class='fa " + css +"'></i>");
                }
                a.attr("lay-id", item.id);
                a.append("<cite>"+item.name+"</cite>");
                var href = item.href;
                if (href!=null && href!=""){
                    a.attr("data-url", href);
                }
                //创建li
                var li = $("<li class='layui-nav-item'></li>");
                if (index == 0) {
                    li.addClass("layui-nav-itemed");
                }
                //把a挂到li上
                li.append(a);
                //把li挂到ul （menu）
                menu.append(li);

                //处理多级菜单
                setChildren(li, item.child);
            })
        }
    });
}

/**
 * 递归地绑定前台菜单
 * @param parentElement
 * @param child
 */
function setChildren(parentElement, child){
    if (child!=null && child.length>0){

        $.each(child, function (index, item) {
            //创建<a>
            var a = $("<a href='javascript:;'></a>");

            var css = item.css;
            if (css!=null && css!=""){
                a.append("<i aria-hidden='true' class='fa " + css +"'></i>");
            }
            a.attr("lay-id", item.id);
            a.append("<cite>"+item.name+"</cite>");
            var href = item.href;
            if (href!=null && href!=""){
                a.attr("data-url", href);
            }

            //创建dd
            var dd = $("<dd></dd>");
            dd.append(a);

            var dl = $("<dl class='layui-nav-child'></dl>");


            dl.append(dd);
            parentElement.append(dl);

            setChildren(dd, item.child);
        });


    }
}


var active;

layui.use(['layer', 'element'], function() {
    var $ = layui.jquery,
        layer = layui.layer;
    var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    element.on('nav(demo)', function(elem){
        //layer.msg(elem.text());
    });

    //触发事件
    active = {
        tabAdd: function(obj){
            var lay_id = $(this).attr("lay-id");
            var title = $(this).html() + '<i class="layui-icon" data-id="' + lay_id + '"></i>';
            //新增一个Tab项
            element.tabAdd('admin-tab', {
                title: title,
                content: '<iframe src="' + $(this).attr('data-url') + '"></iframe>',
                id: lay_id
            });
            element.tabChange("admin-tab", lay_id);
        },
        tabDelete: function(lay_id){
            element.tabDelete("admin-tab", lay_id);
        },
        tabChange: function(lay_id){
            element.tabChange('admin-tab', lay_id);
        }
    };
    //添加tab
    $(document).on('click','a',function(){
        if(!$(this)[0].hasAttribute('data-url') || $(this).attr('data-url')===''){
            return;
        }
        var tabs = $(".layui-tab-title").children();
        var lay_id = $(this).attr("lay-id");

        for(var i = 0; i < tabs.length; i++) {
            if($(tabs).eq(i).attr("lay-id") == lay_id) {
                active.tabChange(lay_id);
                return;
            }
        }
        active["tabAdd"].call(this);
        resize();
    });

    //iframe自适应
    function resize(){
        var $content = $('.admin-nav-card .layui-tab-content');
        $content.height($(this).height() - 147);
        $content.find('iframe').each(function() {
            $(this).height($content.height());
        });
    }
    $(window).on('resize', function() {
        var $content = $('.admin-nav-card .layui-tab-content');
        $content.height($(this).height() - 147);
        $content.find('iframe').each(function() {
            $(this).height($content.height());
        });
    }).resize();

    //toggle左侧菜单
    $('.admin-side-toggle').on('click', function() {
        var sideWidth = $('#admin-side').width();
        if(sideWidth === 200) {
            $('#admin-body').animate({
                left: '0'
            });
            $('#admin-footer').animate({
                left: '0'
            });
            $('#admin-side').animate({
                width: '0'
            });
        } else {
            $('#admin-body').animate({
                left: '200px'
            });
            $('#admin-footer').animate({
                left: '200px'
            });
            $('#admin-side').animate({
                width: '200px'
            });
        }
    });

    //手机设备的简单适配
    var treeMobile = $('.site-tree-mobile'),
        shadeMobile = $('.site-mobile-shade');
    treeMobile.on('click', function () {
        $('body').addClass('site-mobile');
    });
    shadeMobile.on('click', function () {
        $('body').removeClass('site-mobile');
    });
});