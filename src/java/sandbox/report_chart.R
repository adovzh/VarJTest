# parse arguments
args <- commandArgs(trailingOnly=TRUE)
report_var <- if (length(args) > 0) args[1] else "Latency"

# read data
report <- read.csv("test_locks_report.csv")

# prepare additional variables
specs <- levels(report$Spec)
nspecs <- length(specs)
xrange <- range(report$Number)
yrange <- range(report[[report_var, exact=FALSE]])

# set up the plot
pdf(paste("chart_", tolower(report_var), ".pdf", sep=""))
plot(xrange, yrange, type="n", xlab="Number of threads", ylab=report_var)
colors <- rainbow(nspecs)
i <- 1

# add lines
for (spec in specs) {
	specset <- subset(report, Spec == spec)
	lines(specset$Number, specset[[report_var, exact=FALSE]], type="b", col=colors[i])
	i <- i + 1
}

# add title
title("Fucking chart I am pissed of")

# good bye
dev.off()

