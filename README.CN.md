

# 设计


## 倒排索引

倒排索引（Inverted Index）是搜索引擎的核心结构，它维护了<词(Term)，包含该词的文档信息列表（Postings List）> 之间的对应关系，
Postings List 中的每个元素分别对应了包含该词的文档的唯一编号 `DocId`。

## 词典

为了能够在倒排索引中查找文档，我们需要先找到 Term 所对应的 Postings List ，因此我们首选需要构建词典（Term Dictionary）。

词典中每一个词都有一个对应的 `TermInfo`，它维护了以下信息：

* 告诉我们包含 Term 的文档总数
* 告诉我们 Term 对应的 Postings List