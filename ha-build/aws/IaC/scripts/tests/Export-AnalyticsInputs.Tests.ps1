$ErrorActionPreference = 'Stop'
$workspace = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
$exportScript = Join-Path $workspace 'scripts/Export-AnalyticsInputs.ps1'
$fixtureOutput = Join-Path $workspace ('.tmp-export-' + [guid]::NewGuid().ToString('N') + '.json')
$global:IacExportFixtureMode = 'valid'

function terraform {
    param([Parameter(ValueFromRemainingArguments = $true)][string[]]$Arguments)
    if (($Arguments | Select-Object -Last 3) -join ' ' -ne 'output -json analytics_inputs') {
        throw 'The exporter must only request the dedicated root output.'
    }
    if ($global:IacExportFixtureMode -eq 'failed') {
        $global:LASTEXITCODE = 1
        return
    }
    $global:LASTEXITCODE = 0
    $fixture = [ordered]@{
        project_name = 'fixture-project'
        aws_region = 'ap-northeast-1'
        app_env = 'dev'
        source_bucket_name = 'fixture-source-bucket'
        source_data_prefix = 'monthly/healthinfo/'
        athena_result_prefix = 'monthly/athena-results/'
        unexpected_secret = 'must-not-be-exported'
    }
    if ($global:IacExportFixtureMode -eq 'wrong-environment') { $fixture['app_env'] = 'prd' }
    if ($global:IacExportFixtureMode -eq 'missing-output') { $fixture.Remove('source_bucket_name') }
    return $fixture | ConvertTo-Json
}

try {
    & $exportScript -Environment dev -OutputPath $fixtureOutput | Out-Null
    $contents = [IO.File]::ReadAllText($fixtureOutput)
    $parsed = $contents | ConvertFrom-Json
    if (@($parsed.PSObject.Properties).Count -ne 6 -or $parsed.source_bucket_name -ne 'fixture-source-bucket' -or $contents.Contains('unexpected_secret')) {
        throw 'The exporter did not preserve the six-field non-secret contract.'
    }
    $bytes = [IO.File]::ReadAllBytes($fixtureOutput)
    if ($bytes.Length -gt 2 -and $bytes[0] -eq 239 -and $bytes[1] -eq 187 -and $bytes[2] -eq 191) {
        throw 'The generated tfvars JSON contains a UTF-8 BOM.'
    }
    foreach ($failureMode in @('failed', 'wrong-environment', 'missing-output')) {
        $global:IacExportFixtureMode = $failureMode
        $rejected = $false
        try { & $exportScript -Environment dev -OutputPath $fixtureOutput | Out-Null } catch { $rejected = $true }
        if (-not $rejected -or [IO.File]::ReadAllText($fixtureOutput) -ne $contents) {
            throw "Invalid input '$failureMode' was accepted or overwrote the existing file."
        }
    }
    Write-Output 'Exporter checks passed: contract, UTF-8 encoding, and three failure cases.'
} finally {
    if ($fixtureOutput.StartsWith($workspace + [IO.Path]::DirectorySeparatorChar, [StringComparison]::OrdinalIgnoreCase) -and (Test-Path -LiteralPath $fixtureOutput)) {
        Remove-Item -LiteralPath $fixtureOutput
    }
    Remove-Variable -Name IacExportFixtureMode -Scope Global
}
