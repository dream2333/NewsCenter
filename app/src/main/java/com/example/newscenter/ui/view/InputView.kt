//package com.example.newscenter.ui.view
//
//import androidx.compose.runtime.Composable
//
////密码可显示切换
//@Composable
//private fun TestTextFiled3() {
//    val textValue = remember {//创建一个可变变量来保存文本框输入状态的变化
//        mutableStateOf("")
//    }
//    val pwdOff = remember {//保存密码可见性状态变化
//        mutableStateOf(false)
//    }
//    val icon = if (pwdOff.value) {
//        Icons.Filled.Clear
//    } else {
//        Icons.Filled.Add
//    }
//    OutlinedTextField(value = textValue.value,
//        label = {//设置标签
//            Text("密码")
//        },
//        leadingIcon = {//设置文本框前端图标
//            Icon(Icons.Filled.Lock, contentDescription = null)
//        },
//        trailingIcon = {//设置文本框尾端图标
//            IconButton(//添加一个可点击的图标来切换密码的可见性
//                onClick = {
//                    pwdOff.value = !pwdOff.value
//                }) {
//                Icon(icon, contentDescription = "")
//            }
//        },
//        placeholder = {//设置提示文本
//            Text(text = "请输入密码")
//        },
//        //visualTransformation设置文本的视觉格式
//        visualTransformation = (
//                if (pwdOff.value)
//                    VisualTransformation.None//普通文本样式
//                else
//                    PasswordVisualTransformation()//密码样式，星号显示文字
//                ),
//        onValueChange = {
//            textValue.value = it//更改变量值，才能显示在页面上
//        }
//    )
//
//}
