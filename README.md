# crawler-collection

个人爬虫练习项目整合仓库，用于统一归档过去写过的各类采集脚本与新闻抓取项目。

这个仓库的重点是保留历史项目、整理目录结构、统一管理 GitHub 仓库数量，而不是把旧代码强行重构成一个可运行的大型系统。这里保留的是不同阶段的练手项目、实验代码和学习痕迹。

## 仓库定位

- 个人爬虫项目归档合集
- 覆盖 Java 与 Python 两类历史实现
- 保留原始结构，尽量少改动旧代码
- 以展示、留档、占坑为主，不追求统一运行入口

## 收录项目

### Java

1. `projects/java/search-boot`
   - 最初版 Spring Boot 新闻采集项目
   - 包含定时抓取、MySQL 入库、关键词搜索、Redis 热搜排行

2. `projects/java/bmw-searchboot`
   - `search-boot` 的后续整理版
   - 结构更完整，代码更接近正式项目形态

### Python

1. `projects/python/weibo-spider`
   - 微博用户信息与微博内容采集脚本
   - 单文件脚本，偏早期练手项目

2. `projects/python/forum-image-scraper`
   - 论坛帖子图片下载脚本
   - 来自早期 Python 爬虫练习

## 目录结构

```text
crawler-collection/
|-- projects/
|   |-- java/
|   |   |-- search-boot/
|   |   `-- bmw-searchboot/
|   `-- python/
|       |-- weibo-spider/
|       `-- forum-image-scraper/
|-- .gitignore
`-- README.md
```

## 整理说明

- 已移除各旧仓库自己的 `.git`
- 已移除 `target/`、`.idea/`、`.settings/` 等构建和 IDE 产物
- 已将明显的历史密码改为占位值，避免直接提交到公开仓库
- 代码主体尽量保持原样，方便回顾当时的实现方式

## 使用说明

- 这是一个代码归档仓库，不是统一框架下的单体应用
- 部分目标站点、接口、代理配置已经过时，代码不保证现在仍可直接运行
- 如果后续还想继续整理，可以在这个仓库基础上再做二次分类或重构
