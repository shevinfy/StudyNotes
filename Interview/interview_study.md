# 面试指南：

## 参考图书：

《剑指offer》





## 文章内容简介：

1. **提高代码质量**，规范性、完整性和鲁棒性
2. 可利用**画图，举例，分解**解决复杂难题
3. **先清晰思路，再动手编程**
4. **优化时间和空间效率**
5. **面试表现学习和沟通能力**
6. **培养知识迁移、抽象建模和发散思维能力**
7. **面试官期待行为**



希望读这篇文章的朋友不要只学一些技巧来应付面试，==而是通过学习如何解决面试中的难题来提高自己的编程和解决问题的能力==

## 导读：

剑指offer一共有7章。

### 第一章**介绍面试的流程**：

通常面试的过程可以分为**电话面试、共享桌面远程面试和现场面试**。

每轮面试又分为**行为面试、技术面试和应聘者提问**。

### 第二章**梳理应聘者在接受技术面试时写出高质量代码的3个要点**：

**编程语言、数据结构、算法**这3个方面总结了程序员面试的知识点。



### 第三章**评论应聘者再面试时写出高质量代码的3个要点**：

面试官期待应聘者写出的代码能够完成基本的功能，还能应对特殊情况并对非法输入进行合理的处理。

读者将学会如何**规范性、完整性和鲁棒性**3个方面提高代码的质量。



### 第四章总结在编程面试中解决难题的常用思路：

如果在面试过程汇总遇到复杂的难题，写代码之前形成清晰的思路。

**将学会画图、举例和分解这3种思路来解决问题。**



### 第五章介绍如何优化代码的时间效率和空间效率：

我们最好要找到一个问题的最优解法

**学会优化时间效率和空间换时间的常用算法。**



### 第六章总结面试中的各项技能：

面试官会一直关注应聘者的**学习能力和沟通能力**

面试官还喜欢考察**知识迁移能力、抽象建模能力和发散思维能力。**



### 第七章面试案例：

知道哪些是面试不好的举动，哪些又是面试官期待的行为。

少犯错误和不犯错误。





## 第一章、面试的流程：

### 1.1、大厂面试官谈面试：

1. 会考查**算法和数据结构**
2. 会考查**专业技能和项目经验**
3. 考查对**公司近况和项目情况**是否有所了解
4. **准备好合适的问题问面试官**
5. 不要过于紧张，这有助于**后面解决问题时开阔思路**
6. 不要基于编写代码，应该**先了解清楚所要解决的问题**。
7. 写完代码后不要马上提交，**最好自己检查并借助一些测试用例来测试几遍代码**，找出可能出现的错误。
8. 应聘技术岗位就是要**踏实**写程序。



### 1.2、面试的3种形式

#### 1.2.1、电话面试

电话面试有些面试官会跟你约好时间，但是有些面试官喜欢**突击检查**。所以当我们提交面试后，要时刻保持手机电量足够**打电话2个小时**，并且我们尽量**不要长时间呆在喧杂的环境**下。

电话面试**最大的难题就是我们难于表达**，因为我们只能靠自己的说话让面试官明白，所以我们在电话面试的时候要尽可能用**形象化的语言把细节说清楚。**

比如：我们在说一棵二叉树的时候，**就需要把二叉树中有哪些节点，每个节点的左子节点是什么？右子节点是什么？这些都要说清楚**

#### 1.2.2、共享桌面远程面试

远程桌面面试面试官最关心的就是**应聘者的编程习惯以及调试能力**。

##### ·思考清楚再开始编码！！

再没有清晰的思路之前写出的代码通常会漏洞百出，如果写出的漏洞被面试官发现，我们就会心乱，后面代码越改越乱。

最好的策略就是应聘者先想清楚解决问题的思路。

如算法的空间、时间复杂度各是什么，有哪些特殊情况需要处理，然后再动手写代码。

##### ·良好的代码命名和缩进对齐的习惯

一目了然的变量和方法名，加上合理的缩进和括号对齐，又让别人觉得有大量的参与项目的经验。

##### ·==能够进行单元测试==

这个是很重要的！

如果我们能在**定义好函数之后立即对该函数进行全面的单元测试**，那么就相当于向面试官证明了自己有着专业的软件开发经验，如果我们可以==先写单元测试用例，在写解决问题的函数，==那么相信面试官一定会对你另眼相看的。因为可以做到测试在前开发在后的程序员实在是太稀缺了，他肯定会毫不犹豫地抛出橄榄枝。



我们运行代码的时候发现结果不对之后，面试官也很注重我们的表现，我们此时的反应、采取的措施都能体现出我们的调试功底。

我们要熟**练地设置断点、单步跟踪、查看内存、分析调用栈，**就很快能发现问题的根源并最终解决问题。

调试能力在书上是学不到的，如果我们有很好的调试能力，面试官将会觉得我们的开发经验很丰富。

==所以调试功底是否扎实很重要。==



#### 1.2.3、现场面试

##### 规划好路线并估算出行时间！

##### 准备好得体的衣服

##### 注意面试邀请函里的面试流程。

##### 准备几个问题问面试官



### 1.3、面试的3和环节

行为面试、技术面试、应聘者提问

#### 1.3.1、行为面试环节