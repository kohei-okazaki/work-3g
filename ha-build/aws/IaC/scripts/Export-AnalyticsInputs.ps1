[CmdletBinding()]
param(
    [ValidateSet('dev', 'stg', 'prd')]
    [string]$Environment = 'dev',
    [string]$OutputPath
)

$ErrorActionPreference = 'Stop'
$iacRoot = Split-Path -Parent $PSScriptRoot
$applicationsDirectory = Join-Path $iacRoot "01_applications/environments/$Environment"
$analyticsDirectory = Join-Path $iacRoot "02_analytics/environments/$Environment"

if (-not (Test-Path -LiteralPath $applicationsDirectory -PathType Container)) {
    throw "Application environment does not exist: $applicationsDirectory"
}
if ([string]::IsNullOrWhiteSpace($OutputPath)) {
    $OutputPath = Join-Path $analyticsDirectory 'application.auto.tfvars.json'
}
$destination = [IO.Path]::GetFullPath($OutputPath)
if (-not (Test-Path -LiteralPath (Split-Path -Parent $destination) -PathType Container)) {
    throw "Output directory does not exist: $(Split-Path -Parent $destination)"
}

# Select only the dedicated, non-secret root output; never export the whole state.
$outputLines = & terraform "-chdir=$applicationsDirectory" output -json analytics_inputs
if ($LASTEXITCODE -ne 0) {
    throw 'Could not read analytics_inputs. Initialize and apply the application platform first.'
}
$values = ($outputLines -join "`n") | ConvertFrom-Json
$sharedNames = @('project_name', 'aws_region', 'app_env', 'source_bucket_name', 'source_data_prefix', 'athena_result_prefix')
$sharedInputs = [ordered]@{}
foreach ($name in $sharedNames) {
    $property = $values.PSObject.Properties[$name]
    if ($null -eq $property -or $property.Value -isnot [string] -or [string]::IsNullOrWhiteSpace($property.Value)) {
        throw "Missing or invalid application output: $name"
    }
    $sharedInputs[$name] = $property.Value
}
if ($sharedInputs['app_env'] -ne $Environment) {
    throw "Application output environment does not match '$Environment'."
}

[IO.File]::WriteAllText($destination, ($sharedInputs | ConvertTo-Json) + "`n", (New-Object Text.UTF8Encoding($false)))
Write-Output "Exported application inputs to $destination"
