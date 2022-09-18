# Git 实践笔记

## 第一次在文件拉取github or gitee项目

```git
git clone 项目地址(https://github.....)
```

这里好像要登录

## 第一次上传本地文件到github上

```git
git add .
git commit -m "备注（前缀用insert:  update: delete:）"
# 连接远程git仓库 第一上传就会让你连接.
# git remote add 分支名 项目地址
git remote add master https://git....
# git push 分支名
git push master
```

当别人上传了项目,你就不能上传了,要先更新(pull)项目才能上传



## 第一次pull项目

remote:代表远程库名
branch:远程分支的名称

```git
# 远程分支与本地分支关联
# git branch --set-upstream-to=<remote>/<branch> master
git branch --set-upstream-to=origin/master
# 拉取最新项目
git pull
```

拉取最新项目就可以继续上传项目了





## 修改最后一次commit的信息

git commit --amend --message ="修改信息"