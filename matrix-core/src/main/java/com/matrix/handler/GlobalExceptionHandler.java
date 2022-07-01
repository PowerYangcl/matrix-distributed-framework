package com.matrix.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import javax.validation.UnexpectedTypeException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.ErrorCode;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.validate.Vmsg;


/**
 * @description: 全局异常捕获
 * 
 * @author Yangcl
 * @date 2022-3-4 18:39:24
 * @home https://github.com/PowerYangcl
 * @path matrix-core / com.matrix.validate.ValidationErrorHandler.java
 * @version 1.6.0.8-validation
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseClass {
	
    @Autowired
    private MessageSource i18n;

	@ResponseBody
    @ExceptionHandler({HttpMediaTypeException.class, HttpMediaTypeNotAcceptableException.class})
    public Result<?> handleHttpMediaTypeException(HttpServletRequest request, HttpMediaTypeException e) {
		System.out.println("HttpMediaTypeException");
		e.printStackTrace();
        return Result.ERROR(this.getInfo(100010129), ResultCode.INVALID_ARGUMENT);		// 100010129=出现捕获GlobalExceptionHandler全局异常
    }
	
	@ResponseBody
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<?> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
		System.out.println("HttpRequestMethodNotSupportedException");
		e.printStackTrace();
        return Result.ERROR(this.getInfo(100010129), ResultCode.INVALID_ARGUMENT);		// 100010129=出现捕获GlobalExceptionHandler全局异常
    }
	
	@ResponseBody
    @ExceptionHandler({UnexpectedTypeException.class})
    public Result<?> handleUnexpectedTypeException(HttpServletRequest request, UnexpectedTypeException ex) {
		String message = "系统代码错误：" + ex.getMessage();
        return Result.ERROR(message, ResultCode.INVALID_ARGUMENT);
    }
	
	/**
	 * @description: Service层参数验证，如果调用matrix-api则用到此处
	 * 		@ Valid 和@ Validated的service层的应用：
	 * 			1、service【接口】的方法参数上添加注解@Valid；
	 * 			2、service【实现类】上加注解 @ Validated service实现的方法参数上加注解 @ Valid
	 * 			3、:方法参数对象上加参数的限制注解
	 * 		参考：https://www.cnblogs.com/newAndHui/p/14185091.html
	 * 
	 * @author Yangcl
	 * @date 2022-3-11 15:39:59
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.8-validation
	 */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
    	List<Vmsg> list = new ArrayList<Vmsg>();
    	Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
    	for(ConstraintViolation<?> cv : set) {
    		Class<? extends Annotation> annotationType = cv.getConstraintDescriptor().getAnnotation().annotationType();
    		String[] annoArr = annotationType.getName().split("\\.");		// javax.validation.constraints.NotBlank
    		String msg = cv.getMessage();			// 100020111
    		Path propertyPath = cv.getPropertyPath(); 
    		String[] split = propertyPath.toString().split("\\.");		// ajaxValidationTest.dto.target
    		Vmsg vm = new Vmsg();
    		vm.setFiled(split[split.length - 1]);
    		vm.setAnnotation(annoArr[annoArr.length - 1]);
    		vm.setMsg(this.getInfo(Long.valueOf(msg)));
    		list.add(vm);
    	}
    	
    	String msg = "ConstraintViolationException in param validation. " + JSONArray.toJSONString(list);
        return Result.ERROR(msg, ResultCode.PARAM_VALIDATION_FAILED);
    }
    
    /**
     * @description: Controller入口层面进行参数验证。
     * 
     * @author Yangcl
     * @date 2022-3-11 15:41:46
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.8-validation
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(HttpServletRequest request, BindException ex) {
    	BindingResult binding_result = ex.getBindingResult();
    	Class<?> target_class = binding_result.getTarget().getClass();
    	String className = target_class.getName();
    	
    	List<Vmsg> list = new ArrayList<Vmsg>();
    	List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
    	for(ObjectError e : errorList) {
    		JSONObject parse = JSONObject.parseObject(JSONObject.toJSON(e.getArguments()[0]).toString());
    		Vmsg vm = new Vmsg();
    		vm.setFiled(parse.getString("defaultMessage"));
    		vm.setAnnotation(e.getCode());
    		vm.setMsg(this.getInfo(Long.valueOf(e.getDefaultMessage())));
    		list.add(vm);
    	}
    	String msg = className + ":BindException in param validation. " + JSONArray.toJSONString(list);
        return Result.ERROR(msg, ResultCode.PARAM_VALIDATION_FAILED);
    }
    
    /**
     * @description: 如果你使用了@ RequestBody @ Valid 来封装参数并校验，
     * 		此时BindException这个异常处理器又不起作用，需要增加MethodArgumentNotValidException来处理。
     * 
     * @author Yangcl
     * @date 2022-4-18 16:42:41
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.8-validation
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
    	BindingResult bindingResult = ex.getBindingResult();
    	Class<?> targetClass = bindingResult.getTarget().getClass();
    	String className = targetClass.getName();
    	List<Vmsg> list = new ArrayList<Vmsg>();
    	List<ObjectError> errorList = bindingResult.getAllErrors();
    	for(ObjectError e : errorList) {
    		JSONObject parse = JSONObject.parseObject(JSONObject.toJSON(e.getArguments()[0]).toString());
    		Vmsg vm = new Vmsg();
    		vm.setFiled(parse.getString("defaultMessage"));
    		vm.setAnnotation(e.getCode());
    		vm.setMsg(this.getInfo(Long.valueOf(e.getDefaultMessage())));
    		list.add(vm);
    	}
    	String msg = className + ":MethodArgumentNotValidException in param validation. " + JSONArray.toJSONString(list);
        return Result.ERROR(msg, ResultCode.PARAM_VALIDATION_FAILED);
    }
    
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException e) {
    	System.out.println("MissingServletRequestParameterException");
    	e.printStackTrace();
        return Result.ERROR(this.getInfo(100010129), ResultCode.INVALID_ARGUMENT);		// 100010129=出现捕获GlobalExceptionHandler全局异常
    }
    
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
    	System.out.println("HttpMessageNotReadableException");
    	e.printStackTrace();
        return Result.ERROR(this.getInfo(100010129), ResultCode.INVALID_ARGUMENT);		// 100010129=出现捕获GlobalExceptionHandler全局异常
    }
    
    @ResponseBody
    @ExceptionHandler(HttpStatusCodeException.class)
    public Result<?> handleHttpStatusCodeException(HttpServletRequest request, HttpStatusCodeException e) {
    	System.out.println("HttpStatusCodeException");
    	e.printStackTrace();
        return Result.ERROR(this.getInfo(100010129), ResultCode.INVALID_ARGUMENT);		// 100010129=出现捕获GlobalExceptionHandler全局异常
    }
    
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public Result<?> handleMultipartException(HttpServletRequest request, MultipartException e) {
    	System.out.println("MultipartException");
    	e.printStackTrace();
        return Result.ERROR(this.getInfo(100010129), ResultCode.INVALID_ARGUMENT);		// 100010129=出现捕获GlobalExceptionHandler全局异常
    }
    
    @ResponseBody
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public Result<?> handleUnsatisfiedServletRequestParameterExceptionn(HttpServletRequest request, UnsatisfiedServletRequestParameterException e) {
    	System.out.println("UnsatisfiedServletRequestParameterException");
    	e.printStackTrace();
        return Result.ERROR(this.getInfo(100010129), ResultCode.INVALID_ARGUMENT);		// 100010129=出现捕获GlobalExceptionHandler全局异常
    }
    
    
//    @ResponseBody		TODO 是否开启？
//    @ExceptionHandler(Throwable.class)
    public Result<?> handleError(HttpServletRequest request, Throwable e) {
    	System.out.println("HttpServletRequest");
    	return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<?> handlerMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
    	System.out.println("MethodArgumentTypeMismatchException");
    	e.printStackTrace();
        return Result.ERROR(this.getInfo(100010129), ResultCode.INVALID_ARGUMENT);		// 100010129=出现捕获GlobalExceptionHandler全局异常
    }
    
    
    private Result<?> handleFieldErrors(BindingResult binding_result, Locale locale) {
        Class<?> target_class = binding_result.getTarget().getClass();
        FieldError field_error = binding_result.getFieldError();
        Field field = getField(target_class, field_error.getField());
        if (field == null) {
            String error_code = ErrorCode.MISMATCH_ARGUMENT_TYPE;
            String message = i18n.getMessage(field_error.getDefaultMessage(), new Object[]{field_error.getField()}, locale);
            return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
        }
//        String field_name = getSerializedName(field.getName());			TODO 此处未验证
        String field_name = null;
        String error_type = field_error.getCode();
        if ("Range".equals(error_type) && field.getAnnotation(Range.class) != null) {
            Range range = field.getAnnotation(Range.class);
            String range_format = String.format("[%d,%d]", range.min(), range.max());
            String error_code = ErrorCode.OUT_OF_RANGE;
            String message = i18n.getMessage(error_code, new Object[]{field_name, range_format}, locale);
            return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
        } else if ("Length".equals(error_type) && field.getAnnotation(Length.class) != null) {
            Length length = field.getAnnotation(Length.class);
            String length_format = String.format("[%d,%d]", length.min(), length.max());
            String error_code = ErrorCode.OUT_OF_RANGE;
            String message = i18n.getMessage(error_code, new Object[]{field_name, length_format}, locale);
            return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
        } else if ("NotNull".equals(error_type) || "NotBlank".equals(error_type)) {
            String error_code = ErrorCode.MISSING_ARGUMENT;
            String message = i18n.getMessage(error_code,
                    new Object[]{field_name}, locale);
            return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
        } else if (TypeMismatchException.ERROR_CODE.equals(error_type)) {
            String error_code = ErrorCode.INVALID_ARGUMENT;
            String message = i18n.getMessage(error_code, new Object[]{field_name}, locale);
            return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
        }
        String error_code = ErrorCode.INVALID_ARGUMENT;
        String message = i18n.getMessage(field_error.getDefaultMessage(), new Object[]{field_error.getRejectedValue()}, locale);
        return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }


    private Field getField(Class<?> clazz, String field_name) {
        if (clazz == null || StringUtils.isBlank(field_name)) {
            return null;
        }
        Class<?> sub_clazz = null;
        String sub_field_name = null;
        if (field_name.contains(".")) {
            int index = field_name.indexOf('.');
            sub_field_name = field_name.substring(index + 1);
            field_name = field_name.substring(0, index).replaceAll("\\[\\d+\\]", "");
        }
        Field field = ReflectionUtils.findField(clazz, field_name);
        if (field != null) {
            sub_clazz = getActualTypeOfField(field);
            Field sub_field = getField(sub_clazz, sub_field_name);
            if (sub_field != null) {
                field = sub_field;
            }
        }
        return field;
    }

    private Class<?> getActualTypeOfField(Field field) {
        Class<?> type = field.getType();
        if (type == List.class) {
            Type generic_type = field.getGenericType();
            if (generic_type instanceof ParameterizedType) {
                Type[] type_arguments = ((ParameterizedType) generic_type).getActualTypeArguments();
                if (type_arguments != null && type_arguments.length > 0) {
                    return (Class<?>) type_arguments[0];
                }
            }
        }
        return type;
    }
}
































