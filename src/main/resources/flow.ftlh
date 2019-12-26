digraph flowdot {
<#list nodes as node>
    <#if node.style == "START">
    ${node.name}[shape="rectangle",style="rounded,filled",color="limegreen",label="${node.label}",fontname="微软雅黑"];
    </#if>
    <#if node.style == "END">
    ${node.name}[shape="rectangle",style="rounded,filled",color="lightcoral",label="${node.label}",fontname="微软雅黑"];
    </#if>
    <#if node.style == "TASK">
    ${node.name}[shape="rectangle",style="filled",color="deepskyblue",label="${node.label}",fontname="微软雅黑"];
    </#if>
    <#if node.style == "IFTASK">
    ${node.name}[shape="diamond",style="filled",color="plum",label="${node.label}",fontname="微软雅黑"];
    </#if>
</#list>
<#list nodes as node>
    <#if (node.next)??>
        <#if (node.next)?contains("|")>
            <#assign nextList=node.next?split("|")>
            <#assign yes=nextList[0]>
            <#assign no=nextList[1]>
    ${node.name} -> ${yes}[arrowhead = "open", label = "是",  fontname="微软雅黑"];
    ${node.name} -> ${no}[arrowhead = "open", label = "否",  fontname="微软雅黑"];
        <#else>
            <#if (node.comment)??>
    ${node.name} -> ${node.next}[arrowhead = "open", label = "${node.comment}",  fontname="微软雅黑"];
            <#else>
    ${node.name} -> ${node.next}[arrowhead = "open",fontname="微软雅黑"];
            </#if>
        </#if>
    </#if>
</#list>
}