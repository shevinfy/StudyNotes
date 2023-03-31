![image-20230324122426934](DFS%E6%B7%B1%E5%BA%A6%E4%BC%98%E5%85%88%E7%AE%97%E6%B3%95.assets/image-20230324122426934.png)

# DFS介绍：

全排列可以用深度优先遍历（DFS）

dfs（）：函数名

参数：1.char[] p 原本的图点

 2.bool[] pd 需要一个辅助的元素来保证某个点排列过了 用过是true 没用过是false

3.int level 判断截止条件，到最后一层截止。

4.char[] res最终存储结果的数组

p就是原本的，res是我们dfs处理后的。



## 写DFS有两个主要的点：

### 1.截止条件

截止条件就是level遍历到最后一层。

```
if（level == p.length+1）{
	
}
```



### 2.遍历候选节点

候选节点就是从头节点开始的其他节点

```
for(int i=0;i<p.length;i++){
	int c = p[i];
	//2.1 筛选
	if（！pd[i]）{  pb是判断这个节点有没有被使用过
		res.push(c);
		pb[i] = true; //表示这个节点被使用过了。
		dfs(p,pb,level+1,res);  //递归，寻找level+1下一层节点
		res.pop();
		pb[i] = false;
	}
}
```





定义p  pd  level=1 res



# 例题：

