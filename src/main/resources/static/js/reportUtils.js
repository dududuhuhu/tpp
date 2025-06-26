/**
 * 报告弹窗显示工具模块
 * 用于在页面中以统一样式展示 AI 风险分析报告等结构化内容
 */

function showReportDialog(title, data) {
    layer.open({
        type: 1,
        title: title || '风险分析报告',
        skin: 'layui-layer-molv',
        area: ['800px', '600px'],
        maxmin: true,
        shadeClose: true,
        scrollbar: true,
        content: `
            <div style="padding: 24px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: #333;">
                <div style="border: 1px solid #16baaa; border-radius: 8px; padding: 24px; background: #f9fefc; box-shadow: 0 0 8px rgba(22,186,170,0.2);">
                    <h2 style="text-align: center; color: #16baaa; margin-bottom: 24px;">${title || '风险分析报告'}</h2>
                    <p><strong style="color:#16baaa;">主机 MAC：</strong> <span style="font-weight: 600;">${data.mac || '-'}</span></p>

                    <section style="margin-top: 20px;">
                        <h3 style="color:#ff5722; border-left: 4px solid #ff5722; padding-left: 8px;">风险摘要</h3>
                        <p style="margin-top: 8px; line-height: 1.6;">${data.riskSummary || '无'}</p>
                    </section>

                    <section style="margin-top: 20px;">
                        <h3 style="color:#f57c00; border-left: 4px solid #f57c00; padding-left: 8px;">风险原因</h3>
                        <p style="margin-top: 8px; line-height: 1.6;">${data.reason || '无'}</p>
                    </section>

                    <section style="margin-top: 20px;">
                        <h3 style="color:#e91e63; border-left: 4px solid #e91e63; padding-left: 8px;">可能后果</h3>
                        <p style="margin-top: 8px; line-height: 1.6;">${data.consequence || '无'}</p>
                    </section>

                    <section style="margin-top: 20px;">
                        <h3 style="color:#4caf50; border-left: 4px solid #4caf50; padding-left: 8px;">修复建议</h3>
                        <p style="margin-top: 8px; line-height: 1.6;">${data.suggestion || '无'}</p>
                    </section>
                </div>
            </div>
        `
    });
}

// 将函数暴露为模块全局变量
window.reportUtils = {
    showReportDialog
};
