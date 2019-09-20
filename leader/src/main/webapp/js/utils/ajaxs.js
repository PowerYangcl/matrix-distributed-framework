
var ajaxs = {
    /**
     * @descriptions 封装同步ajax请求
     *
     * @param type_  必填post or get，一般默认填写post
     * @param url_ 所请求的后台路径  var url_ =  "login/login.do";
     * @param data_ 请求参数  {name:name_ , password:'0001'}  or $("#the-form-id").serializeArray()，一般选择form序列化的数组
     * @returns json 后台向页面返回的json字符串
     *
     * @date 2016年5月23日上午10:34:27
     * @author Yangcl
     * @version 1.0.1
     */
    sendAjax:function(type_, url_ , data_){
        var msg_ = null;
        $.ajax({
            dataType : "text",
            type :type_,
            url : url_,
            data : data_,
            async : false,
            success : function(msg) {
                msg_ = msg;
            },
            error: function(msg) {
                msg_ = msg;
            }
        });
        return msg_;
    },
    
    /**
     * @descriptions 封装异步ajax请求
     *
     * @param type_  必填post or get，一般默认填写post
     * @param url_ 所请求的后台路径  var url_ =  "login/login.do";
     * @param data_ 请求参数  {name:name_ , password:'0001'}  or $("#the-form-id").serializeArray()，一般选择form序列化的数组
     * @returns json 后台向页面返回的json字符串
     *
     * @date 2016年5月23日上午10:34:27
     * @author Yangcl
     * @version 1.0.1
     */
    asyncAjax:function(type_, url_ , data_){
        var msg_ = null;
        $.ajax({
            dataType : "text",
            type :type_,
            url : url_,
            data : data_,
            async : true,
            success : function(msg) {
                msg_ = msg;
            },
            error: function(msg) {
                msg_ = msg;
            }
        });
        return msg_;
    }
}








