<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet"
          target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<#if code.isTimeRestricted()>
    <span id="time_restriction">${code.getTime()}</span>
</#if>

<#if code.isViewsResctricted()>
    <span id="views_restriction">${code.getViews()}</span>
</#if>



<span id="load_date">
${code.getDate()}
</span>

<pre id="code_snippet">
    <code>${code.getCode()}</code>

</pre>

</body>
</html>