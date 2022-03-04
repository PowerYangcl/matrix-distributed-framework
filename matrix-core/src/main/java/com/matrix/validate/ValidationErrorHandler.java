package com.matrix.validate;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

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

import com.matrix.base.BaseClass;
import com.matrix.base.ErrorCode;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;


/**
 * @description: 全局异常捕获
 * 		https://blog.csdn.net/f641385712/article/details/97402946\
 * 		https://www.cnblogs.com/xuwujing/p/10933082.html
 * 		http://cache.baiducontent.com/c?m=JY8vvW09J3uiuaqPBL7j5gqby-cJdyXgq370BbUktSQyc9qlUGmJ0ExCh6LvUCoLspdYNewxu7CuLmNXX-OnWJW_KnnPZjOXtyAbFkIG7a4Gj4GNeURfnH9c3ZyNu3uC6-h3aIXwUQWeoU0OkqpHTGscwhAQZ3es4nvok9LbVPv-mTXDkTnKGWq9ZjlWDAQvL2FsB9Kd6YsqOFhX6KI9Aa&p=9765d311879712a05abd9b7d0d169e&newp=9b769a479f934eac58edf82f1141a5231610db2151d7d3126b82c825d7331b001c3bbfb422201506d5c47c6202a8495ae1f73079370923a3dda5c91d9fb4c57479c1&s=cfcd208495d565ef&user=baidu&fm=sc&query=spring+validation%D7%D4%B6%A8%D2%E5%B7%B5%BB%D8%B8%F1%CA%BD&qid=ef5bbee300001589&p1=4
 * 
 * @author Yangcl
 * @date 2022-3-4 18:39:24
 * @home https://github.com/PowerYangcl
 * @path matrix-core / com.matrix.validate.ValidationErrorHandler.java
 * @version 1.6.0.8-validation
 */
@ControllerAdvice
public class ValidationErrorHandler extends BaseClass {			// TODO BaseClass是否能生效？
	
    @Autowired
    private MessageSource i18n;

	@ResponseBody
    @ExceptionHandler({HttpMediaTypeException.class, HttpMediaTypeNotAcceptableException.class})
    public Result<?> handleHttpMediaTypeException(HttpServletRequest request, HttpMediaTypeException e) {
		
        return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
	
	@ResponseBody
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<?> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
		
        return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
	
	
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
    	
        return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(HttpServletRequest request, BindException e) {
    	
        return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
    	
        return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException e) {
    	
        return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
    	
        return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    @ResponseBody
    @ExceptionHandler(HttpStatusCodeException.class)
    public Result<?> handleHttpStatusCodeException(HttpServletRequest request, HttpStatusCodeException e) {
    	
    	return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public Result<?> handleMultipartException(HttpServletRequest request, MultipartException e) {
    	
    	return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    @ResponseBody
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public Result<?> handleUnsatisfiedServletRequestParameterExceptionn(HttpServletRequest request, UnsatisfiedServletRequestParameterException e) {
    	
    	return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    
//    @ResponseBody		TODO 是否开启？
//    @ExceptionHandler(Throwable.class)
    public Result<?> handleError(HttpServletRequest request, Throwable e) {
    	
    	return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
    }
    
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<?> handlerMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
    	
    	return Result.ERROR(this.getInfo(600010060), ResultCode.INVALID_ARGUMENT);
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
































