document.querySelectorAll('.nav-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));
        document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
        btn.classList.add('active');
        document.getElementById(btn.dataset.page).classList.add('active');

        if (btn.dataset.page === 'dashboard')
            loadDashboard();
        if (btn.dataset.page === 'batteries')
            loadBatteries();
        if (btn.dataset.page === 'analysis')
            loadAnalysisOptions();
        if (btn.dataset.page === 'decision')
            initDecisionSupport();
    });
});